package com.example.calendarexample1.Decorators

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.example.calendarexample1.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade

class RangeDecorator(
    private val context: Context,
    private val color: Int,
    private var dates: Collection<CalendarDay>
): ParentDecorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.background_middle_date)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            v.addSpan(ForegroundColorSpan(Color.BLACK))
        }

        drawable?.let {
            view?.setBackgroundDrawable(it)
            view?.setSelectionDrawable(it)
            view?.addSpan(boldSpan)
        }

//        view?.addSpan( StyleSpan(Typeface.BOLD))
//        view?.addSpan( RelativeSizeSpan(1.4f))
    }

//    // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
//    override fun shouldDecorate(day: CalendarDay?): Boolean {
//        Log.i(MainActivity.TAG_C, "shouldDecorate ${day?.date}")
//        return true
//    }
//
//    // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
//    override fun decorate(view: DayViewFacade?) {
//        drawable?.let {
//            Log.i(MainActivity.TAG_C, "decorate ${view}")
//            view?.setSelectionDrawable(it)
//        }
////            view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
//    }
}