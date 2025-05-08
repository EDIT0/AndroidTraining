package com.hs.workation.core.component.calendar

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.hs.workation.core.resource.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DisableDecorator(
    private val context: Context,
    private var dates: Collection<CalendarDay>?
): Decorator() {

    private var drawable: Drawable? = null

    init {
        drawable = ContextCompat.getDrawable(context, R.drawable.bg_calendar_base)
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        /**
         * [default] 오늘 이전 날짜는 선택 불가.
         * 그 외 disable 하고 싶은 날짜는 따로 추가
         */
        return dates?.contains(day) == true || day?.date!! < CalendarDay.today().date
    }

    override fun decorate(view: DayViewFacade?) {
        view?.let { v ->
            v.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, com.hs.workation.core.resource.R.color.blue_grey_200)))
            v.addSpan(lineThrough)
            v.setDaysDisabled(true)

            drawable?.let {
                v.setSelectionDrawable(it)
    //            view?.addSpan(boldSpan)
            }
        }
    }
}