package com.example.calendarexample1.Decorators

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.calendarexample1.MainActivity.Companion.TAG_C
import com.example.calendarexample1.R
import com.example.calendarexample1.shape.CustomDotSpan
import com.example.calendarexample1.shape.CustomRectangle
import com.example.calendarexample1.shape.CustomRectangle2
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class AddEventDecorator2(context: Context, color: Int, dates: ArrayList<CalendarDay>, duplicatesList: ArrayList<CalendarDay>) : ParentDecorator() {

    private lateinit var context: Context
    private var color: Int = 0
    private var dates = HashSet<CalendarDay>()
    private var drawable: Drawable? = null
    private var duplicatesList = ArrayList<CalendarDay>()

    init {
        this.context = context
        this.color = color
        this.dates = HashSet(dates)
        drawable = ContextCompat.getDrawable(this.context, R.drawable.background_base)
        this.duplicatesList = duplicatesList
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        Log.i(TAG_C, "AddDecorator2 ${day?.date} ${dates.contains(day)}")
        if(duplicatesList.contains(day)) {
            Log.i(TAG_C, "AddDecorator2 적용 안된다.")
            return false
        } else {
            return dates.contains(day)
        }
    }

    override fun decorate(view: DayViewFacade?) {
        drawable?.let { d ->
            view?.setSelectionDrawable(d)
            view?.addSpan(boldSpan)
            view?.addSpan(CustomRectangle2(5f, R.color.teal_200))
        }

    }
}