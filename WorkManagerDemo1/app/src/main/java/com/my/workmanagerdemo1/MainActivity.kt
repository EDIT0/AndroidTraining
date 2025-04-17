package com.my.workmanagerdemo1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.my.workmanagerdemo1.databinding.ActivityMainBinding
import com.my.workmanagerdemo1.util.SharedPreferencesUtil
import com.my.workmanagerdemo1.util.SharedPreferencesUtil.KEY_PERCENTAGE
import com.my.workmanagerdemo1.util.getSharedViewModel
import com.my.workmanagerdemo1.work.ForegroundService1
import com.my.workmanagerdemo1.work.NoLimitWork1
import com.my.workmanagerdemo1.work.OneTimeWork1
import com.my.workmanagerdemo1.work.PeriodWork1
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var serviceIntent: Intent? = null
    private lateinit var sharedVM: SharedViewModel

    private val scope = lifecycleScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("MYTAG", "${javaClass.simpleName} onCreate()")

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("MYTAG", "handleOnBackPressed")
                finish()
            }
        })

        // Shared Preferences 초기화
        SharedPreferencesUtil.init(applicationContext)

        // ViewModel 초기화
        sharedVM = getSharedViewModel()

        scope.launch {
            sharedVM.commonSharedFlow.collect {
                Log.i("MYTAG", "${this@MainActivity.javaClass.simpleName} ${it}")
            }
        }

        // 포그라운드 서비스 시작
        if(serviceIntent == null) {
            serviceIntent = Intent(this, ForegroundService1::class.java)
        }
        startForegroundService(serviceIntent)

        binding.btnSharedVMCheck.setOnClickListener {
            Log.i("MYTAG", "현재 data1: ${sharedVM.data1} data2: ${sharedVM.data2}")
            scope.launch {
                sharedVM.commonSharedFlow.emit("클릭했다")
            }
        }

        binding.btnOneActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, OneActivity::class.java))
        }

        binding.btnTwoActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, TwoActivity::class.java))
        }

        binding.btnDeleteMain.setOnClickListener {
            WorkManager.getInstance(applicationContext).cancelUniqueWork(NO_LIMIT_WORK_1)
        }

        binding.btnDeleteOne.setOnClickListener {
            WorkManager.getInstance(applicationContext).cancelUniqueWork(ONE_TIME_WORK_1)
        }

        binding.btnDeleteTwo.setOnClickListener {
            WorkManager.getInstance(applicationContext).cancelUniqueWork(NO_LIMIT_WORK_2)
        }

//        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
//        intent.data = Uri.parse("package:" + packageName)
//        startActivity(intent)

        val workConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)  // 배터리가 부족하지 않을 때만 실행
            .setRequiresCharging(false)  // 충전 중이 아닐 때도 실행 가능
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)  // 네트워크 필요 없음
            .build()

        val oneTimeWork1 = OneTimeWorkRequest.Builder(OneTimeWork1::class.java)
            .setConstraints(workConstraints)
            .build()

        val periodWork1 = PeriodicWorkRequestBuilder<PeriodWork1>(
            15, TimeUnit.MINUTES
        )
            .build()

//        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("PeriodWork1", ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, periodWork1)
        lifecycleScope.launch {
            WorkManager.getInstance(applicationContext).getWorkInfoByIdFlow(periodWork1.id)
                .collect { workInfo ->
                    workInfo?.let {
                        val progress = workInfo.progress.getInt("progress", 0)
                        Log.d("MYTAG", "PeriodWork1 ${progress}% ${workInfo}")
                        when(workInfo.state) {
                            WorkInfo.State.ENQUEUED -> {
                                // 실행 대기 중 (조건 충족 시 실행됨) (아직 종료 X)
                            }
                            WorkInfo.State.RUNNING -> {
                                // 현재 실행 중 (아직 종료 X)
                            }
                            WorkInfo.State.SUCCEEDED -> {
                                // 정상적으로 완료됨 (종료됨)
                            }
                            WorkInfo.State.FAILED -> {
                                // 작업이 실패하여 종료됨 (종료됨)
                            }
                            WorkInfo.State.BLOCKED -> {
                                // 다른 Work가 끝나야 실행될 수 있는 상태 (아직 종료 X)
                            }
                            WorkInfo.State.CANCELLED -> {
                                // 사용자가 취소했거나 시스템이 제거함 (종료됨)
                            }
                        }
                    }
                }
        }

        val percentage = SharedPreferencesUtil.getInt(KEY_PERCENTAGE, 0)
        val inputData = Data.Builder()
            .putString("key1", "${javaClass.simpleName}")
            .putInt("key2", percentage)
            .build()

        /**
         * 포그라운드 작업 1 실행
         */
        val noLimitWork1 = OneTimeWorkRequest.Builder(NoLimitWork1::class.java)
            .setConstraints(workConstraints)
            .setInputData(inputData)
            .build()

//        workManager.enqueueUniquePeriodicWork("ForegroundWork1", ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, foregroundWork1)
//        WorkManager.getInstance(applicationContext).enqueueUniqueWork(NO_LIMIT_WORK_1, ExistingWorkPolicy.REPLACE, noLimitWork1)
        lifecycleScope.launch {
            WorkManager.getInstance(applicationContext).getWorkInfoByIdFlow(noLimitWork1.id)
                .collect { workInfo ->
                    workInfo?.let {
                        val progress = workInfo.progress.getInt("progress", 0)
                        Log.d("MYTAG", "NoLimitWork1 ${progress}% ${workInfo}")
                        binding.btnDeleteMain.setText("${progress}% DeleteMain")
//                        NotificationUtil.createNotificationChannel(applicationContext)
//                        NotificationUtil.makeNoti(context = applicationContext, percentage = progress)
                        when(workInfo.state) {
                            WorkInfo.State.ENQUEUED -> {
                                // 실행 대기 중 (조건 충족 시 실행됨) (아직 종료 X)
                            }
                            WorkInfo.State.RUNNING -> {
                                // 현재 실행 중 (아직 종료 X)
                            }
                            WorkInfo.State.SUCCEEDED -> {
                                // 정상적으로 완료됨 (종료됨)
                                binding.btnDeleteMain.setText("100% DeleteMain")
                                SharedPreferencesUtil.remove(KEY_PERCENTAGE)
                            }
                            WorkInfo.State.FAILED -> {
                                // 작업이 실패하여 종료됨 (종료됨)
                                binding.btnDeleteMain.setText("DeleteMain")
                            }
                            WorkInfo.State.BLOCKED -> {
                                // 다른 Work가 끝나야 실행될 수 있는 상태 (아직 종료 X)
                            }
                            WorkInfo.State.CANCELLED -> {
                                // 사용자가 취소했거나 시스템이 제거함 (종료됨)
                                binding.btnDeleteMain.setText("DeleteMain")
                            }
                        }
                    }
                }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("MYTAG", "${javaClass.simpleName} onNewIntent()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MYTAG", "${javaClass.simpleName} onResume()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MYTAG", "${javaClass.simpleName} onStop()")
    }

    override fun onDestroy() {
        Log.d("MYTAG", "${javaClass.simpleName} onDestroy()")
        serviceIntent?.let {
            stopService(it)
        }

        super.onDestroy()
    }
}