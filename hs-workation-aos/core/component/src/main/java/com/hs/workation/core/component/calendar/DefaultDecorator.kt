package com.hs.workation.core.component.calendar

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.hs.workation.core.resource.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DefaultDecorator(
    private val context: Context,
    private var dates: Collection<CalendarDay>
): Decorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.ic_calendar_selector)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            v.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, com.hs.workation.core.resource.R.color.white)))

            drawable?.let {
                v.setBackgroundDrawable(it)
                v.setSelectionDrawable(it)
            }
        }
    }
}