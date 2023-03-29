package com.edit.alarmexample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edit.alarmexample1.Alarm.calculateTime
import com.edit.alarmexample1.Alarm.stringDateToDate
import com.edit.alarmexample1.Alarm.convertDateDayString
import com.edit.alarmexample1.Alarm.convertDatePlusDayString
import com.edit.alarmexample1.Alarm.registerAlarmOneTimePerDay
import com.edit.alarmexample1.Alarm.registerAlarmRepeatPerDay
import com.edit.alarmexample1.Alarm.registerAlarmOneTimePerTime
import com.edit.alarmexample1.Alarm.unregisterAlarm
import com.edit.alarmexample1.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    private val alarmListAdapter = AlarmListAdapter()

    private val timeSetDialogFragment = TimeSetDialogFragment.newInstance("", "")

    lateinit var bottomSheet: View
    lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        alarmListRVSetting()
        observer()
        bottomSheetSetting()


    }

    lateinit var newAlarmData: AlarmModel
    private fun alarmListRVSetting() {
        binding.rvAlarm.layoutManager = LinearLayoutManager(this)
        binding.rvAlarm.adapter = alarmListAdapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                mainViewModel.changePosition(viewHolder.adapterPosition, target.adapterPosition)
                alarmListAdapter.notifyItemMoved(viewHolder.adapterPosition,target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val alarmModel = alarmListAdapter.currentList[position]

                mainViewModel.deleteOneTimeAlarm(alarmModel)
                unregisterAlarm(binding.root.context, alarmModel.id)

//                Snackbar.make(binding.root, "Delete Alarm", Snackbar.LENGTH_LONG).apply {
//                    setAction("되돌리기") {
//                        mainViewModel.saveOneTimeAlarm(alarmModel, position)
//                    }
//                    show()
//                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvAlarm)
        }

        alarmListAdapter.setOnItemClickListener { i, alarmModel ->
            Log.i("MYTAG", "${alarmModel}")
            when(alarmModel.alarmType) {
                AlarmType.ONE_TIME_ALARM_PER_DAY, AlarmType.REPEAT_ALARM_PER_DAY -> {
                    if(alarmModel.isSwitch) {
                        /**
                         * 알람이 켜져있는 경우 off로 만들어야한다.
                         * 이 알람이 울리는 날짜 상관없이 시간만 잘 저장하고 isSwitch off 해주면 된다.
                         * id를 이용해 알람 등록을 해제한다.
                         * */
                        val newAlarmData = AlarmModel(
                            alarmModel.alarmType,
                            alarmModel.id,
                            stringDateToDate(convertDateDayString(alarmModel.finishDate)).toString(),
                            calculateTime(convertDateDayString(alarmModel.finishDate)),
                            false
                        )
                        mainViewModel.changeAlarmList(newAlarmData, i)
                        if(alarmModel.alarmType == AlarmType.ONE_TIME_ALARM_PER_DAY) {
                            unregisterAlarm(binding.root.context, alarmModel.id)
                        } else if(alarmModel.alarmType == AlarmType.REPEAT_ALARM_PER_DAY) {
                            unregisterAlarm(binding.root.context, alarmModel.id)
                        }

                        PreferencesManager.putAlarmList(binding.root.context, mainViewModel.alarmList.value as ArrayList<AlarmModel>)
                    } else {
                        /**
                         * 알람이 꺼져있는 경우 on으로 만들어야한다.
                         * 알람을 on 시킬 때는 2가지로 경우로 나뉘게 된다.
                         * */
                        Log.i("MYTAG", "이거뭐임? ${alarmModel.finishDate}") // Wed Mar 29 21:11:00 GMT+09:00 2023
                        Log.i("MYTAG", convertDateDayString(alarmModel.finishDate)) // 2023-03-30 21:11:00

                        if(calculateTime(convertDateDayString(alarmModel.finishDate)) > 0) {
                            /**
                             * 시간이 현재 시간보다 뒤에 있는 경우
                             * 저장되어 있는 Date를 이용하여 알람을 등록해주면 된다.
                             * */
                            newAlarmData = AlarmModel(
                                alarmModel.alarmType,
                                alarmModel.id,
                                stringDateToDate(convertDateDayString(alarmModel.finishDate)).toString(),
                                calculateTime(convertDateDayString(alarmModel.finishDate)),
                                true
                            )
                        } else {
                            /**
                             * 시간이 현재 시간보다 앞에 있을 경우
                             * 다음날을 이 시간을 계산해서 알람을 등록해준다.
                             * */
                            val date = convertDatePlusDayString(alarmModel.finishDate)
                            newAlarmData = AlarmModel(
                                alarmModel.alarmType,
                                alarmModel.id,
                                stringDateToDate(date).toString(),
                                calculateTime(date),
                                true
                            )
                        }

                        mainViewModel.changeAlarmList(newAlarmData, i)

                        if(alarmModel.alarmType == AlarmType.ONE_TIME_ALARM_PER_DAY) {
                            registerAlarmOneTimePerDay(binding.root.context, alarmModel.id, calculateTime(convertDateDayString(alarmModel.finishDate)))
                        } else if(alarmModel.alarmType == AlarmType.REPEAT_ALARM_PER_DAY) {
                            registerAlarmRepeatPerDay(binding.root.context, alarmModel.id, calculateTime(convertDateDayString(alarmModel.finishDate)))
                        }

                        PreferencesManager.putAlarmList(binding.root.context, mainViewModel.alarmList.value as ArrayList<AlarmModel>)
                    }
                }
                AlarmType.ONE_TIME_ALARM_PER_TIME -> {
                    if(alarmModel.isSwitch) {
                        val newAlarmData = AlarmModel(
                            alarmModel.alarmType,
                            alarmModel.id,
                            alarmModel.finishDate,
                            alarmModel.remainingTime,
                            false,
                            alarmModel.repeatTime
                        )
                        mainViewModel.changeAlarmList(newAlarmData, i)
                        unregisterAlarm(binding.root.context, alarmModel.id)

                        PreferencesManager.putAlarmList(binding.root.context, mainViewModel.alarmList.value as ArrayList<AlarmModel>)
                    } else {
                        newAlarmData = AlarmModel(
                            alarmModel.alarmType,
                            alarmModel.id,
                            alarmModel.finishDate,
                            System.currentTimeMillis(),
                            true,
                            alarmModel.repeatTime
                        )

                        mainViewModel.changeAlarmList(newAlarmData, i)

                        registerAlarmOneTimePerTime(binding.root.context, alarmModel.id, alarmModel.repeatTime)

                        PreferencesManager.putAlarmList(binding.root.context, mainViewModel.alarmList.value as ArrayList<AlarmModel>)
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun observer() {
        mainViewModel.alarmSaveObserver.observe(this as LifecycleOwner) {
            Log.i("MYTAG", "${SystemClock.elapsedRealtime()}")

            when(it) {
                AlarmType.ONE_TIME_ALARM_PER_DAY -> {
                    mainViewModel.apply {
                        val time = calculateTime("${year}-${month}-${day} ${hour}:${minute}:${seconds}")

                        while(true) {
                            val randomId = mainViewModel.rand(0, 10000)
                            val a = isNumberInArrayList(randomId, mainViewModel.alarmList.value as ArrayList<AlarmModel>)
                            if(a) {

                            } else {
                                id = randomId
                                break
                            }
                        }

                        Log.i("MYTAG", "${id} / ${time}")

                        addAlarmList(AlarmModel(AlarmType.ONE_TIME_ALARM_PER_DAY, id, stringDateToDate("${year}-${month}-${day} ${hour}:${minute}:${seconds}").toString(), time, true))
                        registerAlarmOneTimePerDay(binding.root.context, id, time)

                        PreferencesManager.putAlarmList(binding.root.context, alarmList.value as ArrayList<AlarmModel>)
                    }
                }
                AlarmType.REPEAT_ALARM_PER_DAY -> {
                    mainViewModel.apply {
                        val time = calculateTime("${year}-${month}-${day} ${hour}:${minute}:${seconds}")

                        while(true) {
                            val randomId = mainViewModel.rand(0, 10000)
                            val a = isNumberInArrayList(randomId, mainViewModel.alarmList.value as ArrayList<AlarmModel>)
                            if(a) {

                            } else {
                                id = randomId
                                break
                            }
                        }

                        Log.i("MYTAG", "${id} / ${time}")

                        addAlarmList(AlarmModel(AlarmType.REPEAT_ALARM_PER_DAY, id, stringDateToDate("${year}-${month}-${day} ${hour}:${minute}:${seconds}").toString(), time, true))
                        registerAlarmRepeatPerDay(binding.root.context, id, time)

                        PreferencesManager.putAlarmList(binding.root.context, alarmList.value as ArrayList<AlarmModel>)
                    }
                }
                AlarmType.ONE_TIME_ALARM_PER_TIME -> {
                    mainViewModel.apply {
                        val time = secondsTime.toLong()

                        while(true) {
                            val randomId = mainViewModel.rand(0, 10000)
                            val a = isNumberInArrayList(randomId, mainViewModel.alarmList.value as ArrayList<AlarmModel>)
                            if(a) {

                            } else {
                                id = randomId
                                break
                            }
                        }

                        Log.i("MYTAG", "${id} / ${time} / ${System.currentTimeMillis()}")
                        /**
                         * 여기서 finishDate: String 과 remainingTime: Long 은 이름하고 다른 용도로 사용됌
                         * finishDate는 최초 설정한 시간(초)을 가지고 있음
                         * remainingTime은 시작시간을 가지고 있음
                         * */
                        addAlarmList(AlarmModel(AlarmType.ONE_TIME_ALARM_PER_TIME, id, time.toString(), System.currentTimeMillis(), true, time))
                        registerAlarmOneTimePerTime(binding.root.context, id, time)

                        PreferencesManager.putAlarmList(binding.root.context, alarmList.value as ArrayList<AlarmModel>)
                    }
                }
                else -> {

                }
            }

        }

        mainViewModel.alarmList.observe(this as LifecycleOwner) {
            Log.i("MYTAG", "리스트 갱신")
            alarmListAdapter.submitList(it)
            alarmListAdapter.notifyDataSetChanged()
        }
    }
    private fun isNumberInArrayList(number: Int, array: ArrayList<AlarmModel>): Boolean {
        for (obj in array) {
            if (obj.id == number) {
                return true
            }
        }
        return false
    }

    private fun bottomSheetSetting() {
        bottomSheet = binding.bottomSheetLayout
        bottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheet)
        // true로 하면 Expanded 아니면 Collapsed 임
//        bottomSheetBehavior.isFitToContents = false
        // 중간 상태 설정 (0 ~ 1)
//        bottomSheetBehavior.halfExpandedRatio = 0.25f
        // Expanded 되었을 때 위에서부터 얼마나 띄울건지
//        bottomSheetBehavior.expandedOffset = 100
        // 특정 상태로 설정
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.i(TAG, "STATE_HIDDEN")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.i(TAG, "STATE_EXPANDED")
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment, timeSetDialogFragment)
                            .commit()

                        binding.apply {
                            tvCollapsedTitle.visibility = View.GONE
                            fragment.visibility = View.VISIBLE
                        }
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Log.i(TAG, "STATE_HALF_EXPANDED")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.i(TAG, "STATE_COLLAPSED")
                        binding.apply {
                            tvCollapsedTitle.visibility = View.VISIBLE
                            fragment.visibility = View.GONE
                        }
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.i(TAG, "STATE_DRAGGING")
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        Log.i(TAG, "STATE_SETTLING")
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    override fun onBackPressed() {
        if(binding.tvCollapsedTitle.visibility == View.VISIBLE) {
            super.onBackPressed()
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
//            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }

    override fun onResume() {
        super.onResume()
        updateList()
    }

    fun updateList() {
        val alarmList = PreferencesManager.getAlarmList(binding.root.context)
        if(alarmList.isEmpty()) {
            mainViewModel.setAlarmList(ArrayList<AlarmModel>())
        } else {
            mainViewModel.setAlarmList(PreferencesManager.getAlarmList(binding.root.context))
        }
    }

}