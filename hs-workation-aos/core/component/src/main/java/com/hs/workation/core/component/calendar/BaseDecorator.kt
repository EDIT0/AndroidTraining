package com.hs.workation.core.component.calendar

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.hs.workation.core.resource.R

class BaseDecorator(
    private val context: Context
): Decorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_base)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            v.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, com.hs.workation.core.resource.R.color.black)))

            drawable?.let {
                v.setSelectionDrawable(it)
            }
        }
    }
}