package com.hs.workation.core.component.datetimepicker

import org.threeten.bp.LocalTime

interface DateTimePickerViewContract {
    fun setPickerWithCurrentTime(time: LocalTime)
    fun makeAllDate(monthArray: Array<String>, day28: Array<String>, day30: Array<String>, day31: Array<String>): ArrayList<String>
    fun sortDateByToday(allDates: ArrayList<String>, today: String, thisYear: Int, nextYear: Int): ArrayList<String>
    fun addDayName(dateList: ArrayList<String>, dayDisplayName: Array<String>, currentDay: String): ArrayList<String>
    fun jobCancel()
    fun setPickerDividerHeight(height: Float)
    fun setDateFromTo(from: Int, to: Int)
    fun isLeapYear(year: Int): Boolean
}