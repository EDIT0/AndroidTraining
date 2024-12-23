package com.hs.workation.core.component

import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.hs.workation.core.component.calendar.BaseDecorator
import com.hs.workation.core.component.calendar.DefaultDecorator
import com.hs.workation.core.component.calendar.DisableDecorator
import com.hs.workation.core.component.calendar.RangeEndDateDecorator
import com.hs.workation.core.component.calendar.RangeMiddleDecorator
import com.hs.workation.core.component.calendar.RangeStartDateDecorator
import com.hs.workation.core.component.calendar.SaturdayDecorator
import com.hs.workation.core.component.calendar.SundayDecorator
import com.hs.workation.core.util.LogUtil
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CommonRangeCalendar {
    // 선택 날짜 데이터 sharedFlow 사용
    private val _startDate = MutableSharedFlow<String>()
    val startDate = _startDate.asSharedFlow()

    private val _endDate = MutableSharedFlow<String>()
    val endDate = _endDate.asSharedFlow()

    private val _totalDateCount = MutableSharedFlow<String>()
    val totalDateCount = _totalDateCount.asSharedFlow()

    // TODO : 날짜 포맷은 수정 가능성 있음
    private val formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /** 초기화 */
    private fun initDecorators(
        calendarView: MaterialCalendarView,
        disabledDates: List<CalendarDay>? = null
    ) {
        with(calendarView) {
            removeDecorators() // 데코 초기화

            addDecorators(
                listOf(
                    BaseDecorator(context), // 선택 안된 날짜 데코
                    SundayDecorator(context), // 주말일 경우 다른 데코 적용
                    SaturdayDecorator(context), // 주말일 경우 다른 데코 적용
                    DisableDecorator(context, disabledDates), // 선택 불가 날짜 데코
                )
            )
        }
    }

    /** 선택된 날짜 스타일 적용 */
    private fun setDateRangeValue(
        calendarView: MaterialCalendarView,
        dates: List<CalendarDay>
    ) {
        with(calendarView) {
            for(i in 0 until dates.size + 1) {
                when (i) {
                    0 -> {
                        // 시작 날짜 데코
                        addDecorators(RangeStartDateDecorator(context, listOf(dates.first())))
                    }
                    dates.size -> {
                        // 종료 날짜 데코
                        addDecorators(RangeEndDateDecorator(context, listOf(dates.last())))
                    }
                    else -> {
                        // 나머지 데코
                        addDecorators(RangeMiddleDecorator(context, listOf(dates[i])))
                    }
                }
            }
        }
    }

    /**
     * 달력 커스텀 스타일 적용
     * @param calendarView : 달력 뷰
     * @param disabledDates : 선택 불가한 날짜 리스트
     */
    fun create(
        calendarView: MaterialCalendarView,
        disabledDates: List<CalendarDay>? = null
    ) {
        initDecorators(calendarView, disabledDates) // 초기화

        with (calendarView) {
            /** 타이틀 날짜 포맷 */
            setTitleFormatter { day ->
                "${day.year}년 ${day.month}월"
            }

            /** 달력 기간 선택 리스너 */
            setOnRangeSelectedListener { _, dates ->
                LogUtil.i_dev("CALENDAR : 선택된 날짜 $dates")

                initDecorators(calendarView, disabledDates)
                setDateRangeValue(calendarView, dates)

                findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    // 리스너에서 가져온 데이터는 sharedFlow 로 방출 및 수집
                    _startDate.emit("start : ${dates.first().date.format(formatter)}")
                    _endDate.emit("end : ${dates.last().date.format(formatter)}")
                    _totalDateCount.emit("${dates.size - 1}박 ${dates.size}일")
                }
            }

            /** 달력 단일 선택 리스너 */
            setOnDateChangedListener { _, date, _ ->
                LogUtil.i_dev("CALENDAR : 선택된 날짜 $date")

                initDecorators(calendarView, disabledDates)
                addDecorators(DefaultDecorator(context, listOf(date))) // 단일 선택 날짜 데코
                setDateSelected(date, true)
            }
        }
    }
}