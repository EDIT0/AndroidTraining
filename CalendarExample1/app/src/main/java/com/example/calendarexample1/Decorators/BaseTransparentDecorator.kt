package com.example.calendarexample1.Decorators

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.calendarexample1.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade

class BaseTransparentDecorator(
    private val context: Context
): ParentDecorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.background_base_transparent)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            drawable?.let { d ->
                v.setSelectionDrawable(d)
                v.addSpan(boldSpan)
            }
        }
    }
}