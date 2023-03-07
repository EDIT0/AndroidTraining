package com.example.calendarexample1.Decorators

import android.graphics.Typeface

import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.DayViewDecorator


abstract class ParentDecorator: DayViewDecorator {
    val boldSpan = StyleSpan(Typeface.BOLD)
}