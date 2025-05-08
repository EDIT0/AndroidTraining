package com.hs.workation.core.component.calendar

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.hs.workation.core.resource.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.DayOfWeek

class SaturdayDecorator(
    private val context: Context
): Decorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_base)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val weekDay = day!!.date.dayOfWeek
        return weekDay == DayOfWeek.SATURDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            v.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, com.hs.workation.core.resource.R.color.blue_700)))

            drawable?.let {
                v.setSelectionDrawable(it)
            }
        }
    }
}