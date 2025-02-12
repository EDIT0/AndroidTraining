package com.example.calendarexample1.EventCalendar

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calendarexample1.Decorators.BaseTransparentDecorator
import com.example.calendarexample1.Decorators.SaturdayTransparentDecorator
import com.example.calendarexample1.Decorators.SelectTransparentDecorator
import com.example.calendarexample1.Decorators.SundayTransparentDecorator
import com.example.calendarexample1.MainActivity.Companion.TAG_C
import com.example.calendarexample1.R
import com.example.calendarexample1.databinding.ActivityEventCalendarNoHeaderBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import org.threeten.bp.DayOfWeek
import java.util.Collections

class EventCalendarNoHeaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventCalendarNoHeaderBinding

    private var headerYear = 0
    private var headerMonth = 0

    private var currentSelectedYear = 0
    private var currentSelectedMonth = 0
    private var currentSelectedDay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventCalendarNoHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(TAG_C, "오늘은? " + CalendarDay.today())
        headerYear = CalendarDay.today().year
        headerMonth = CalendarDay.today().month

        currentSelectedYear = CalendarDay.today().year
        currentSelectedMonth = CalendarDay.today().month
        currentSelectedDay = CalendarDay.today().day

        binding.eventCalendarNoHeaderView.state()
            .edit()
            .setFirstDayOfWeek(DayOfWeek.SUNDAY)
            .setMaximumDate(CalendarDay.today())
            .commit()

        updateHeaderDate(currentSelectedYear, currentSelectedMonth)

        binding.eventCalendarNoHeaderView.isPagingEnabled = false
        binding.eventCalendarNoHeaderView.topbarVisible = false

        binding.eventCalendarNoHeaderView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays_2)))

        onClickListener()
        changedListener()

        binding.eventCalendarNoHeaderView.apply {
            removeDecorators()
            addDecorators(SelectTransparentDecorator(this@EventCalendarNoHeaderActivity, Collections.singleton(CalendarDay.today())))
            setDateSelected(CalendarDay.today(), true)
        }
        baseDecorators()
    }

    private fun onClickListener() {
        binding.ibArrowLeft.setOnClickListener {
            minusMonth()
        }

        binding.ibArrowRight.setOnClickListener {
            plusMonth()
        }
    }

    private fun changedListener() {
        binding.eventCalendarNoHeaderView.setOnMonthChangedListener { widget, date ->
            Log.i(TAG_C, "변경된 달? ${date}")

            headerYear = date.year
            headerMonth = date.month

            updateHeaderDate(headerYear, headerMonth)
        }

        binding.eventCalendarNoHeaderView.setOnDateChangedListener { widget, date, selected ->
            Log.i(TAG_C, "선택된 날짜? ${date}")

            currentSelectedYear = date.year
            currentSelectedMonth = date.month
            currentSelectedDay = date.day

            binding.eventCalendarNoHeaderView.apply {
                removeDecorators()
                addDecorators(SelectTransparentDecorator(this@EventCalendarNoHeaderActivity, Collections.singleton(date)))
                setDateSelected(date, true)
            }
            baseDecorators()
        }
    }

    private fun baseDecorators() {
        binding.eventCalendarNoHeaderView.apply {
            addDecorators(BaseTransparentDecorator(this@EventCalendarNoHeaderActivity))
            addDecorators(SundayTransparentDecorator(this@EventCalendarNoHeaderActivity))
            addDecorators(SaturdayTransparentDecorator(this@EventCalendarNoHeaderActivity))
        }
    }

    /**
     * 헤더 날짜 업데이트
     * @param year
     * @param month
     */
    private fun updateHeaderDate(year: Int, month: Int) {
        binding.tvYear.text = "${year}"
        binding.tvMonth.text = String.format("%02d월", month)
    }

    /**
     * 이전 달로 이동
     */
    private fun minusMonth() {
        binding.eventCalendarNoHeaderView.goToPrevious()
    }

    /**
     * 다음 달로 이동
     */
    private fun plusMonth() {
        binding.eventCalendarNoHeaderView.goToNext()
    }
}