package com.example.calendarexample1.EventCalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.calendarexample1.Decorators.BaseDecorator
import com.example.calendarexample1.Decorators.AddEventDecorator
import com.example.calendarexample1.Decorators.AddEventDecorator2
import com.example.calendarexample1.Decorators.AddEventDecorator3
import com.example.calendarexample1.MainActivity
import com.example.calendarexample1.MainActivity.Companion.TAG_C
import com.example.calendarexample1.R
import com.example.calendarexample1.databinding.ActivityEventCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import kotlin.collections.ArrayList

class EventCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventCalendarBinding

    private var calendarDayList: ArrayList<CalendarDay> = ArrayList<CalendarDay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        calendarDayList.add(CalendarDay.today())
//        calendarDayList.add(CalendarDay.from(2023, 3, 25))
//        calendarDayList.add(CalendarDay.today())

        // 첫 시작 요일이 일요일이 되도록 설정
        binding.eventCalendarView.state()
            .edit()
            .setFirstDayOfWeek(DayOfWeek.MONDAY)
            .commit()


        binding.eventCalendarView.topbarVisible = true
        binding.eventCalendarView.setLeftArrow(R.drawable.ic_baseline_arrow_circle_left_24)
        binding.eventCalendarView.setRightArrow(R.drawable.ic_baseline_arrow_circle_right_24)

//        binding.rangeCalendarView.getViewTreeObserver().addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                binding.rangeCalendarView.getViewTreeObserver().removeOnGlobalLayoutListener(this)
//                Toast.makeText(this@RangeCalendarActivity, "${binding.rangeCalendarView.width / 7}\n${binding.rangeCalendarView.height / 7}", Toast.LENGTH_LONG).show()
//            }
//        })

        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        binding.eventCalendarView.setTitleFormatter(
            MonthArrayTitleFormatter(resources.getTextArray(
                R.array.custom_months))
        )
        binding.eventCalendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)))


        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        binding.eventCalendarView.setTitleFormatter { day ->
            // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
            // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendƒarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
            // LocalDate로 변환하는 처리가 필요하다
            val inputText: LocalDate = day?.date!!
            Log.i(MainActivity.TAG_C, "${inputText}")
            var calendarHeaderElements: List<String> = inputText.toString().split("-")
            var calendarHeaderBuilder: StringBuilder = StringBuilder()
            val formatMonth = calendarHeaderElements[1].toInt()

            calendarHeaderBuilder
                .append("${calendarHeaderElements[0]}년")
                .append(" ")
                .append("${formatMonth}월")

            calendarHeaderBuilder.toString()
        }

        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
//        binding.calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        binding.eventCalendarView.setDateTextAppearance(R.style.CalenderViewDateCustomText)
//        binding.calendarView.setWeekDayTextAppearance(R.style.CalenderViewWeekCustomText)

        binding.eventCalendarView.setOnDateChangedListener { widget, date, selected ->
            Log.i(TAG_C, "선택된 날짜? ${date}")
            val builder = AlertDialog.Builder(this@EventCalendarActivity)
            if(calendarDayList.contains(date)) {
                builder.setTitle("알림")
                    .setMessage("삭제하시겠습니까?")
                    .setPositiveButton("예") { dialogInterface, i ->
                        calendarDayList.remove(date)
                        binding.eventCalendarView.removeDecorators()
//                        binding.eventCalendarView.addDecorator(AddEventDecorator(binding.root.context, R.color.blue, calendarDayList, emptyList<CalendarDay>() as ArrayList<CalendarDay>))
                        duplicatesAddDecorator()
                    }
                    .setNegativeButton("추가할건데요?") { _, _ ->
                        calendarDayList.add(date)
                        binding.eventCalendarView.removeDecorators()
                        duplicatesAddDecorator()
                    }
                builder.show()
            } else {
                builder.setTitle("알림")
                    .setMessage("표시하시겠습니까?")
                    .setPositiveButton("예") { dialogInterface, i ->
                        calendarDayList.add(date)
                        binding.eventCalendarView.removeDecorators()
//                        binding.eventCalendarView.addDecorator(AddEventDecorator(binding.root.context, R.color.blue, calendarDayList, emptyList<CalendarDay>() as ArrayList<CalendarDay>))
                        duplicatesAddDecorator()
                    }
                    .setNegativeButton("아니오") { _, _ ->

                    }
                builder.show()
            }

        }
    }

    fun duplicatesAddDecorator() {
        val duplicatesList1 = findAllDuplicates(calendarDayList) as ArrayList
        binding.eventCalendarView.addDecorator(AddEventDecorator(binding.root.context, R.color.blue, calendarDayList, duplicatesList1))
        val duplicatesList2 = findAllDuplicates2(duplicatesList1) as ArrayList
        binding.eventCalendarView.addDecorator(AddEventDecorator2(binding.root.context, R.color.blue, duplicatesList1, duplicatesList2))
        binding.eventCalendarView.addDecorator(AddEventDecorator3(binding.root.context, R.color.blue, duplicatesList2))
    }

    fun findAllDuplicates(array: ArrayList<CalendarDay>): List<CalendarDay> {
        val seen: MutableSet<CalendarDay> = mutableSetOf()
        val resultArray = array.filter { !seen.add(it) }
        Log.i(TAG_C, "findAllDuplicates ${resultArray}")
        return resultArray
    }

    fun findAllDuplicates2(array: ArrayList<CalendarDay>): List<CalendarDay> {
        val seen: MutableSet<CalendarDay> = mutableSetOf()
        val resultArray = array.filter { !seen.add(it) }
        Log.i(TAG_C, "findAllDuplicates2 ${resultArray}")
        return resultArray
    }
}