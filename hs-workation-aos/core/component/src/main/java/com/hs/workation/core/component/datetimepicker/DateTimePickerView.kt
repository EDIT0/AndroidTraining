package com.hs.workation.core.component.datetimepicker

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hs.workation.core.component.R
import com.hs.workation.core.component.databinding.DateTimePickerViewBinding
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.util.ViewSizeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


class DateTimePickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr), DateTimePickerViewContract, CoroutineScope {

    private var binding: DateTimePickerViewBinding = DateTimePickerViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val monthArray by lazy {
        resources.getStringArray(R.array.date_time_picker_month)
    }
    private val day28 by lazy {
        resources.getStringArray(R.array.date_time_picker_day_28)
    }
    private val day30 by lazy {
        resources.getStringArray(R.array.date_time_picker_day_30)
    }
    private val day31 by lazy {
        resources.getStringArray(R.array.date_time_picker_day_31)
    }
    private val dayDisplayName by lazy {
        resources.getStringArray(R.array.date_time_picker_day_display_name)
    }

    private var dateList = ArrayList<String>()
    private var currentYear = 0 // 년도 추적
    private var changeYearIndex = 0 // 년도가 바뀌는 위치의 인덱스

    private val hourArray by lazy {
        resources.getStringArray(R.array.date_time_picker_hour)
    }
    private val minuteArray by lazy {
        resources.getStringArray(R.array.date_time_picker_minute)
    }

    // 리턴할 변수들
    private var returnDate = ""
    private var returnHour = ""
    private var returnMinute = ""
    var changedDateTimeListener: ((String) -> Unit)? = null

    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job

    private var dateFrom: Int? = null
    private var dateTo: Int? = null

    init {
        val today = LocalDate.now()
        LogUtil.d_dev("${today.month.firstDayOfYear(true)} ${today.month.firstDayOfYear(false)} ${today.month.firstMonthOfQuarter()}")
//        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
//        val formattedDate = today.format(formatter)
        val dayOfWeek = today.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()) // 요일

        this.currentYear = today.year // 현재 년도

        var allDates = ArrayList<String>()
        allDates = makeAllDate(this.monthArray, this.day28, this.day30, this.day31) // 모든 날짜 리스트에 추가 (윤년 02.29 제외)

        val todayString = "${today.monthValue}.${today.dayOfMonth}" // MM.DD
        this.dateList = sortDateByToday(allDates, todayString, thisYear = currentYear, nextYear = currentYear + 1)

        changeYearIndex = this.dateList.indexOf("01.01") // 01.01 인덱스 저장

        this.dateList = addDayName(this.dateList, this.dayDisplayName, dayOfWeek) // 요일 추가
        this.returnDate = "${today.year}.${this.dateList[0]}"

        LogUtil.d_dev("날짜 사이즈 ${dateList.size}")

        // Picker에 날짜 적용
        setDatePicker(this.dateList, this.dateFrom?:0, this.dateTo?:this.dateList.lastIndex)

        // Picker에 시 적용
        val hourList = this@DateTimePickerView.hourArray.map {
            "${it}${context.getString(R.string.date_time_picker_hour_title)}"
        }.toTypedArray()
        setHourPicker(hourList, 0, this@DateTimePickerView.hourArray.lastIndex)

        // Picker에 분 적용
        val minuteList = this@DateTimePickerView.minuteArray.map {
            "${it}${context.getString(R.string.date_time_picker_minute_title)}"
        }.toTypedArray()
        setMinutePicker(minuteList, 0, this@DateTimePickerView.minuteArray.lastIndex)


        val time = LocalTime.now()
        setPickerWithCurrentTime(time)

        /**
         * Picker 변경 리스너
         */
        binding.npDate.setOnValueChangedListener { picker, oldVal, newVal ->
            if(newVal >= this.changeYearIndex && today.year == this.currentYear) {
                this.currentYear += 1
            } else if(newVal < this.changeYearIndex && (today.year + 1) == this.currentYear){
                this.currentYear -= 1
            }
            returnDate = "${this.currentYear}.${this.dateList[newVal]}"
            changedDateTimeListener?.invoke("${returnDate} ${returnHour} ${returnMinute}")
            LogUtil.d_dev("${returnDate} ${returnHour} ${returnMinute}")
        }

        binding.npHour.setOnValueChangedListener { picker, oldVal, newVal ->
            returnHour = "${hourArray[newVal]}"
            changedDateTimeListener?.invoke("${returnDate} ${returnHour} ${returnMinute}")
            LogUtil.d_dev("${returnDate} ${returnHour} ${returnMinute}")
        }

        binding.npMinute.setOnValueChangedListener { picker, oldVal, newVal ->
            returnMinute = "${minuteArray[newVal]}"
            changedDateTimeListener?.invoke("${returnDate} ${returnHour} ${returnMinute}")
            LogUtil.d_dev("${returnDate} ${returnHour} ${returnMinute}")
        }
    }

    /**
     * 현재 날짜와 시간에 맞추어 설정
     *
     * @param time 현재 시간
     */
    override fun setPickerWithCurrentTime(time: LocalTime) {
        var setHourIndex = time.hour
        var setMinuteIndex = 0

        // 10분 단위 기준
        setMinuteIndex = when(time.minute) {
            0, in 51 .. 59 -> {
                0
            }
            in 41 .. 50 -> {
                5
            }
            in 31 .. 40 -> {
                4
            }
            in 21 .. 30 -> {
                3
            }
            in 11 .. 20 -> {
                2
            }
            in 1 .. 10 -> {
                1
            }
            else -> {
                0
            }
        }

        /**
         * 1. 분이 51 ~ 59분 사이 -> 시간 + 1
         * 2. 시간이 23시이고 분이 51 ~ 59분 사이 -> 시간 + 1, 만약 시간이 24라면 날짜 재조정
         */
        if(time.minute in 51..59) {
            setHourIndex += 1
            if(setHourIndex == 24) {
                setHourIndex = 0
                val firstValue = this.dateList.removeFirst()
                this.dateList.add(firstValue)

                setDatePicker(this.dateList, this.dateFrom?:0, this.dateTo?:this.dateList.lastIndex)
            }
        }
        LogUtil.d_dev("" + setHourIndex + " / " + setMinuteIndex)

        launch {
            binding.npHour.value = setHourIndex
            binding.npMinute.value = setMinuteIndex
        }

        this.returnHour = this.hourArray[setHourIndex]
        this.returnMinute = this.minuteArray[setMinuteIndex]
    }

    /**
     * 1 ~ 12월 까지 모두 입력
     * 윤년인 경우, 나중에 02.29 추가
     *
     * @param monthArray 1~12월 배열
     * @param day28 1~28일 배열
     * @param day30 1~30일 배열
     * @param day31 1~31일 배열
     * @return 1 ~ 12월과 일까지 모두 포함된 리스트
     */
    override fun makeAllDate(monthArray: Array<String>, day28: Array<String>, day30: Array<String>, day31: Array<String>): ArrayList<String> {
        val newList = ArrayList<String>()
        for(month in 1..monthArray.size) {
            when(monthArray[month-1].toInt()) {
                2 -> {
                    for(day in 1..day28.size) {
                        newList.add("${monthArray[month-1]}.${day28[day-1]}")
                    }
                }
                4, 6, 9, 11 -> {
                    for(day in 1..day30.size) {
                        newList.add("${monthArray[month-1]}.${day30[day-1]}")
                    }
                }
                1, 3, 5, 7, 8, 10, 12 -> {
                    for(day in 1..day31.size) {
                        newList.add("${monthArray[month-1]}.${day31[day-1]}")
                    }
                }
            }
        }

        return newList
    }

    /**
     * 오늘 날짜가 리스트의 첫 번째로 오도록 정렬
     * 윤년 확인하여 02.29 추가할지 말지 결정
     *
     * @param allDates 1 ~ 12월까지 월과 일이 담긴 리스트
     * @param today 오늘 날짜 (MM.DD)
     * @param thisYear 올해 년도
     * @param nextYear 다음해 년도
     * @return 오늘 날짜가 인덱스 0번째에 들어가 있는 리스트
     */
    override fun sortDateByToday(allDates: ArrayList<String>, today: String, thisYear: Int, nextYear: Int): ArrayList<String> {
        val newList = ArrayList<String>()

        val todayMonthDay = today.split(".")
        if(todayMonthDay[0].toInt() > 2) { // 올해 Month가 2월을 넘겼다면
            if(isLeapYear(nextYear)) {
                // 다음해 윤년
                val index = allDates.indexOf("02.28")
                allDates.add(index+1, "02.29")
            }
        } else {
            if(isLeapYear(thisYear)) {
                // 올해 윤년
                val index = allDates.indexOf("02.28")
                allDates.add(index+1, "02.29")
            }
        }

        val todayIndex = allDates.indexOf(today) // 오늘 날짜 인덱스 찾기 (MM.DD)
        newList.addAll(allDates.subList(todayIndex, allDates.size)) // 오늘부터 12.31 까지 추가
        newList.addAll(allDates.subList(0, todayIndex)) // 1.1부터 어제 날짜까지 추가

        return newList
    }

    /**
     * (일) 추가
     *
     * @param dateList 날짜 리스트
     * @param dayDisplayName 요일(월,화,수,목,금,토,일) 배열
     * @param currentDay 오늘의 요일
     * @return 요일이 적용된 DateList
     */
    override fun addDayName(dateList: ArrayList<String>, dayDisplayName: Array<String>, currentDay: String): ArrayList<String> {
        val newList = ArrayList<String>()

        var index = 0
        for(i in 0 until dayDisplayName.size) {
            if(dayDisplayName[i] == currentDay) {
                index = i
                break
            }
        }
        for(i in 0 until dateList.size) {
            newList.add("${dateList[i]} (${dayDisplayName[index]})")
            index += 1
            if(index >= dayDisplayName.size) {
                index = 0
            }
        }

        return newList
    }

    /**
     * Job cancel
     */
    override fun jobCancel() {
        job.cancel()
    }

    /**
     * Divider 높이 설정
     * Android SDK 29 이상
     *
     * @param height
     */
    override fun setPickerDividerHeight(height: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.apply {
                npDate.selectionDividerHeight = ViewSizeUtil.dpToPx(context, height).toInt()
                npHour.selectionDividerHeight = ViewSizeUtil.dpToPx(context, height).toInt()
                npMinute.selectionDividerHeight = ViewSizeUtil.dpToPx(context, height).toInt()
            }
        }
    }

    /**
     * 선택할 수 있는 날짜 범위 조정
     *
     * @param from 오늘부터 (from)일 후부터
     * @param to (from)일부터 (to)일 후까지
     */
    override fun setDateFromTo(from: Int, to: Int) {
        if(from <= to && (from >= 0 && this.dateList.size > from) && (to >= 0 && this.dateList.size > to)) {
            this.dateFrom = from
            this.dateTo = to
        } else {
            this.dateFrom = 0
            this.dateTo = this.dateList.size - 1
            LogUtil.e_dev("from ${from} 이 to ${to} 보다 크면 안됩니다.")
        }

        setDatePicker(this.dateList, this.dateFrom?:0, this.dateTo?:this.dateList.lastIndex)
    }

    /**
     * 윤년 확인
     *
     * @param year
     * @return true: 윤년, false: 평년
     */
    override fun isLeapYear(year: Int): Boolean {
        return (year%4 == 0 && year%100 != 0) || (year%400 == 0)
    }

    private fun setDatePicker(dateList: ArrayList<String>, dateFrom: Int, dateTo: Int) {
        binding.npDate.apply {
            minValue = dateFrom
            maxValue = dateTo
            wrapSelectorWheel = false
            displayedValues = dateList.toTypedArray()
        }
    }

    private fun setHourPicker(hourList: Array<String>, dateFrom: Int, dateTo: Int) {
        binding.npHour.apply {
            minValue = dateFrom
            maxValue = dateTo
            wrapSelectorWheel = false
            displayedValues = hourList
        }
    }

    private fun setMinutePicker(minuteList: Array<String>, dateFrom: Int, dateTo: Int) {
        binding.npMinute.apply {
            minValue = dateFrom
            maxValue = dateTo
            wrapSelectorWheel = false
            displayedValues = minuteList
        }
    }
}