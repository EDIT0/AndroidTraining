package com.example.calendarexample1.Decorators

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.example.calendarexample1.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.DayOfWeek

class SaturdayDecorator(
    private val context: Context
): ParentDecorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.background_base)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val date = day!!.date
        // 2. DayOfWeek 객체 구하기
        val dayOfWeek: DayOfWeek = date.dayOfWeek
        // 3. 숫자 요일 구하기
        val dayOfWeekNumber = dayOfWeek.value
        /**
         * 2:월, 3:화, 4:수, 5:목, 6:금, 7:토, 1:일 (월요일 시작 기준)
         * */
//        Log.i(MainActivity.TAG_C, "" + day.date + " / " + Calendar.SUNDAY + " / " + dayOfWeekNumber)
        return dayOfWeekNumber == 7
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            v.addSpan(ForegroundColorSpan(Color.BLUE))
            drawable?.let { d ->
                v.setSelectionDrawable(d)
                v.addSpan(boldSpan)
            }
        }
    }
}