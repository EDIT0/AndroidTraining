package com.example.calendarexample1.RangeCalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Toast
import com.example.calendarexample1.Decorators.*
import com.example.calendarexample1.MainActivity
import com.example.calendarexample1.R
import com.example.calendarexample1.databinding.ActivityMainBinding
import com.example.calendarexample1.databinding.ActivityRangeCalendarBinding
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.util.*

class RangeCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRangeCalendarBinding

    private var startPointDeco : DayViewDecorator? = null
    private var startDateDeco: DayViewDecorator? = null
    private var endDateDeco: DayViewDecorator? = null
    private var rangeDeco: DayViewDecorator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRangeCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 첫 시작 요일이 일요일이 되도록 설정
        binding.rangeCalendarView.state()
            .edit()
            .setFirstDayOfWeek(DayOfWeek.MONDAY)
            .commit()


        binding.rangeCalendarView.topbarVisible = true
        binding.rangeCalendarView.setLeftArrow(R.drawable.ic_baseline_arrow_circle_left_24)
        binding.rangeCalendarView.setRightArrow(R.drawable.ic_baseline_arrow_circle_right_24)

//        binding.rangeCalendarView.getViewTreeObserver().addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                binding.rangeCalendarView.getViewTreeObserver().removeOnGlobalLayoutListener(this)
//                Toast.makeText(this@RangeCalendarActivity, "${binding.rangeCalendarView.width / 7}\n${binding.rangeCalendarView.height / 7}", Toast.LENGTH_LONG).show()
//            }
//        })

        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        binding.rangeCalendarView.setTitleFormatter(MonthArrayTitleFormatter(resources.getTextArray(R.array.custom_months)))
        binding.rangeCalendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)))


        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        binding.rangeCalendarView.setTitleFormatter { day ->
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
        binding.rangeCalendarView.setDateTextAppearance(R.style.CalenderViewDateCustomText)
//        binding.calendarView.setWeekDayTextAppearance(R.style.CalenderViewWeekCustomText)

        // 요일 선택 시 내가 정의한 드로어블이 적용되도록 함
        binding.rangeCalendarView.setOnRangeSelectedListener { widget, dates ->
            // 아래 로그를 통해 시작일, 종료일이 어떻게 찍히는지 확인하고 본인이 필요한 방식에 따라 바꿔 사용한다
            // UTC 시간을 구하려는 경우 이 라이브러리에서 제공하지 않으니 별도의 로직을 짜서 만들어내 써야 한다
            val startDay = dates[0].date.toString()
            val endDay = dates[dates.size - 1].date.toString()
            Log.i(MainActivity.TAG_C, "${dates}")
            Log.e(MainActivity.TAG_C, "시작일 : " + startDay + ", 종료일 : " + endDay)

            binding.rangeCalendarView.apply {
                removeDecorators()
                baseDecorators()
                addDecorators(StartDateDecorator(this@RangeCalendarActivity, R.color.purple_500, Collections.singleton(dates[0])))
                for(i in 1 until dates.size - 1) {
//                binding.calendarView.addDecorators(EventDecorator(R.color.purple_500, Collections.singleton(dates[i])))
                    addDecorators(RangeDecorator(this@RangeCalendarActivity, R.color.purple_500, Collections.singleton(dates[i])))
                }
                addDecorators(EndDateDecorator(this@RangeCalendarActivity, R.color.purple_500, Collections.singleton(dates[dates.size - 1])))
            }
        }

        binding.rangeCalendarView.setOnDateChangedListener { widget, date, selected ->
            Log.i(MainActivity.TAG_C, "선택된 날짜 ${date}")
//            binding.calendarView.addDecorator(
//                EventDecorator(R.color.purple_500, Collections.singleton(date))
//            )
            binding.rangeCalendarView.apply {
                removeDecorators()
                addDecorators(StartPointDecorator(this@RangeCalendarActivity, R.color.purple_500, Collections.singleton(date)))
                setDateSelected(date, true)
            }
            baseDecorators()
//            binding.rangeCalendarView.removeDecorators()
//            binding.rangeCalendarView.addDecorators(StartPointDecorator(this, R.color.purple_500, Collections.singleton(date)))
//            binding.rangeCalendarView.setDateSelected(date, true)
//            baseDecorators()
        }


        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
//        binding.calendarView.addDecorators(DayDecorator(this))
        baseDecorators()
    }

    private fun baseDecorators() {
        binding.rangeCalendarView.apply {
            addDecorators(BaseDecorator(this@RangeCalendarActivity))
            addDecorators(SundayDecorator(this@RangeCalendarActivity))
            addDecorators(SaturdayDecorator(this@RangeCalendarActivity))
        }
    }

    private fun initializationRange() {
        binding.rangeCalendarView.apply {
            startPointDeco?.let { startPoint ->
                removeDecorator(startPoint)
                startDateDeco?.let { startDate ->
                    removeDecorator(startDate)
                    endDateDeco?.let { endDate ->
                        removeDecorator(endDate)
                        rangeDeco?.let { range ->
                            removeDecorator(range)
                        }
                    }
                }
            }
        }
    }
}