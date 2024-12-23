package com.hs.workation.core.component.calendar

import android.graphics.Typeface
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.DayViewDecorator

abstract class Decorator: DayViewDecorator {
    val boldSpan = StyleSpan(Typeface.BOLD)
    val lineThrough = StrikethroughSpan()
}