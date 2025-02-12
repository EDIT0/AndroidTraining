package com.example.calendarexample1.Decorators

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.example.calendarexample1.R
import com.example.calendarexample1.shape.CustomSelectSpan
import com.example.calendarexample1.util.ViewSizeUtil
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade

class SelectTransparentDecorator(
    private val context: Context,
    private var dates: Collection<CalendarDay>
): ParentDecorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.background_base)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            v.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.white)))
        }
        drawable?.let {
//            view?.setBackgroundDrawable(it)
            view?.setSelectionDrawable(it)
            view?.addSpan(boldSpan)
            view?.addSpan(CustomSelectSpan(ViewSizeUtil.dpToPx(context, 12f).toFloat(), ContextCompat.getColor(context, R.color.teal_700)))
        }
//        view?.addSpan( StyleSpan(Typeface.BOLD))
//        view?.addSpan( RelativeSizeSpan(1.4f))
    }
}