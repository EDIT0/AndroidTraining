package com.example.calendarexample1.Decorators

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.calendarexample1.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DayDecorator(
    private val context: Context
): ParentDecorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector)
    }

    // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
    override fun shouldDecorate(day: CalendarDay?): Boolean {
//        Log.i(TAG_C, "shouldDecorate ${day}")
        return true
    }

    // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
    override fun decorate(view: DayViewFacade?) {
        drawable?.let {
//            Log.i(TAG_C, "decorate ${view}")
            view?.setSelectionDrawable(it)
            view?.setSelectionDrawable(it)
            view?.addSpan(boldSpan)
        }
//            view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
    }
}