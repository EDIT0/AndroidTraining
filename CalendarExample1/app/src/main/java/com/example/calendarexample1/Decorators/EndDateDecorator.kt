package com.example.calendarexample1.Decorators

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.example.calendarexample1.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade

class EndDateDecorator(
    private val context: Context,
    private val color: Int,
    private var dates: Collection<CalendarDay>
): ParentDecorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.background_end_date)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            v.addSpan(ForegroundColorSpan(Color.WHITE))
        }

        drawable?.let {
            view?.setBackgroundDrawable(it)
            view?.setSelectionDrawable(it)
            view?.addSpan(boldSpan)
        }

//        view?.addSpan( StyleSpan(Typeface.BOLD))
//        view?.addSpan( RelativeSizeSpan(1.4f))
    }
}