package com.hs.workation.core.component

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.workation.core.component.calendar.BaseDecorator
import com.hs.workation.core.component.calendar.DefaultDecorator
import com.hs.workation.core.component.calendar.DisableDecorator
import com.hs.workation.core.component.calendar.RangeEndDateDecorator
import com.hs.workation.core.component.calendar.RangeMiddleDecorator
import com.hs.workation.core.component.calendar.RangeStartDateDecorator
import com.hs.workation.core.component.calendar.SaturdayDecorator
import com.hs.workation.core.component.calendar.SundayDecorator
import com.hs.workation.core.component.databinding.CalendarRangeBottomSheetBinding
import com.hs.workation.core.util.LogUtil
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

/**
 * 캘린더 바텀 시트
 *
 * @property initialDates : 선택된 날짜 초기값
 * @property resultCallback : 버튼 클릭 시 콜백
 */
class CommonRangeCalendarSheet(
    private val initialDates: MutableState<List<CalendarDay>?> = mutableStateOf(null),
    private val resultCallback: (List<CalendarDay>, String) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: CalendarRangeBottomSheetBinding

    private lateinit var behavior: BottomSheetBehavior<*>

    // TODO : 날짜 포맷은 수정 가능성 있음
    private val formatter = DateTimeFormatter.ofPattern("M.d (EEE)", Locale.KOREAN)

    // 커스텀 변수
    private lateinit var days: String // ex: 1박
    private lateinit var nights: String // ex: 12.4 (화) - 12.5 (수)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CalendarRangeBottomSheetBinding.inflate(inflater)

        setTitle(binding.title, binding.content)
        setCloseButton(binding.btnClose)
        initBookButton(binding.btnBook)
        create(binding.calendarRange)

        return binding.root
    }

    private fun setFormattedDate(dates: List<CalendarDay>) {
        days = "${dates.first().date.format(formatter)} - ${dates.last().date.format(formatter)}"
        nights = "${dates.size - 1}박"
    }

    /** 타이틀 세팅 */
    private fun setTitle(title: TextView, content: TextView) {
        initialDates.value?.let { initialDates ->
            setFormattedDate(initialDates)
            title.text = nights
            content.text = days
        } ?: apply {
            title.text = "날짜를 선택하세요"
            content.text = "-"
        }
    }

    /** 닫기 버튼 동작 */
    private fun setCloseButton(btnClose: ImageButton) {
        btnClose.setOnClickListener {
            dismiss()
        }
    }

    /** 커스텀 버튼 동작 */
    private fun initBookButton(btnBook: Button) {
        with(btnBook) {
            // 날짜 초기값이 있거나 없을 경우 분기
            initialDates.value?.let { dates ->
                setDateString(dates)
            } ?: apply {
                text = "날짜를 선택하세요"
                isEnabled = false

                setBackgroundColor(
                    resources.getColor(com.hs.workation.core.common.R.color.grey_400, null)
                )

                setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    /** 날짜 선택된 경우 커스텀 버튼 스타일 적용 */
    private fun setDateString(dates: List<CalendarDay>) {
        setFormattedDate(dates)

        with(binding.btnBook) {
            text = "$days • $nights"
            isEnabled = true

            setBackgroundColor(
                resources.getColor(com.hs.workation.core.common.R.color.grey_900, null)
            )

            setOnClickListener {
                resultCallback(dates, text.toString())
                dismiss()
            }
        }
    }

    /** 초기화 */
    private fun initDecorators(
        calendarView: MaterialCalendarView,
        disabledDates: List<CalendarDay>? = null
    ) {
        with(calendarView) {
            removeDecorators() // 데코 초기화

            addDecorators(
                listOf(
                    BaseDecorator(context), // 선택 안된 날짜 데코
                    SundayDecorator(context), // 주말일 경우 다른 데코 적용
                    SaturdayDecorator(context), // 주말일 경우 다른 데코 적용
                    DisableDecorator(context, disabledDates), // 선택 불가 날짜 데코
                )
            )
        }
    }


    /** 선택된 날짜 스타일 적용 */
    private fun setDateRangeValue(
        calendarView: MaterialCalendarView,
        dates: List<CalendarDay>
    ) {
        with(calendarView) {
            for (i in 0 until dates.size + 1) {
                when (i) {
                    0 -> {
                        // 시작 날짜 데코
                        addDecorators(RangeStartDateDecorator(context, listOf(dates.first())))
                    }
                    dates.size -> {
                        // 종료 날짜 데코
                        addDecorators(RangeEndDateDecorator(context, listOf(dates.last())))
                    }
                    else -> {
                        // 나머지 데코
                        addDecorators(RangeMiddleDecorator(context, listOf(dates[i])))
                    }
                }
            }
        }
    }

    /**
     * 달력 커스텀 스타일 적용
     * @param calendarView : 달력 뷰
     * @param disabledDates : 선택 불가한 날짜 리스트
     */
    fun create(
        calendarView: MaterialCalendarView,
        disabledDates: List<CalendarDay>? = null
    ) {
        initDecorators(calendarView, disabledDates) // 초기화

        // 선택된 초기 값이 있을 경우
        initialDates.value?.let {
            setDateRangeValue(calendarView, it)
        }

        with(calendarView) {
            /** 타이틀 날짜 포맷 */
            setTitleFormatter { day ->
                "${day.year}년 ${day.month}월"
            }

            /** 달력 기간 선택 리스너 */
            setOnRangeSelectedListener { _, dates ->
                LogUtil.i_dev("CALENDAR : 선택된 날짜 $dates")

                initDecorators(calendarView, disabledDates)
                setDateRangeValue(calendarView, dates)
                setDateString(dates)
            }

            /** 달력 단일 선택 리스너 */
            setOnDateChangedListener { _, date, _ ->
                LogUtil.i_dev("CALENDAR : 선택된 날짜 $date")

                initDecorators(calendarView, disabledDates)
                addDecorators(DefaultDecorator(context, listOf(date))) // 단일 선택 날짜 데코
                setDateSelected(date, true)
            }
        }
    }

    /** 바텀 시트 다이얼로그 초기화 */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface: DialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    /** 바텀 시트 UI 설정 */
    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
//        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
        try {
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dialog?.let { onDismiss(it) }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** 바텀 시트 높이 설정 */
    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 7 / 10
    }

    /** 디바이스 높이 구하기 */
    private fun getWindowHeight(): Int { // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
