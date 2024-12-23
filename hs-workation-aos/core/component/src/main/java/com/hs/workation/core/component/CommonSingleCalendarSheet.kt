package com.hs.workation.core.component

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.workation.core.component.calendar.BaseDecorator
import com.hs.workation.core.component.calendar.DefaultDecorator
import com.hs.workation.core.component.calendar.DisableDecorator
import com.hs.workation.core.component.calendar.SaturdayDecorator
import com.hs.workation.core.component.calendar.SundayDecorator
import com.hs.workation.core.component.databinding.CalendarSingleBottomSheetBinding
import com.hs.workation.core.util.LogUtil
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

/**
 * 캘린더 바텀 시트
 *
 * @property title : 시트 제목
 * @property resultCallback : 버튼 클릭 시 콜백
 */
class CommonSingleCalendarSheet(
    private val title: String,
    private val resultCallback: (String) -> Unit
): BottomSheetDialogFragment() {

    private lateinit var binding: CalendarSingleBottomSheetBinding

    private lateinit var behavior: BottomSheetBehavior<*>

    // TODO : 날짜 포맷은 수정 가능성 있음
    private val formatter = DateTimeFormatter.ofPattern("yyyy.M.d (EEE)", Locale.KOREAN)

    // 선택 날짜 데이터 sharedFlow 사용
    private val _startDate = MutableSharedFlow<String>()
    val startDate = _startDate.asSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CalendarSingleBottomSheetBinding.inflate(inflater)

        setTitle()
        setCloseButton()
        initBookButton()
        create(binding.calendarSingle)

        return binding.root
    }

    /** 타이틀 세팅 */
    private fun setTitle() {
        binding.title.text = title
    }

    /** 닫기 버튼 동작 */
    private fun setCloseButton() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    /** 커스텀 버튼 동작 */
    private fun initBookButton() {
        with (binding.btnBook) {
            text = "날짜를 선택하세요"
            isEnabled = false

            setBackgroundColor(resources.getColor(com.hs.workation.core.common.R.color.grey_400, null))

            setOnClickListener {
                resultCallback(text.toString())
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

        with (calendarView) {
            /** 타이틀 날짜 포맷 */
            setTitleFormatter { day ->
                "${day.year}년 ${day.month}월"
            }

            /** 달력 단일 선택 리스너 */
            setOnDateChangedListener { _, date, _ ->
                LogUtil.i_dev("CALENDAR : 선택된 날짜 $date")

                initDecorators(calendarView, disabledDates)
                addDecorators(DefaultDecorator(context, listOf(date))) // 단일 선택 날짜 데코
                setDateSelected(date, true)

                findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    // 리스너에서 가져온 데이터는 sharedFlow 로 방출 및 수집
                    _startDate.emit("start : ${date.date.format(formatter)}")

                    with(binding.btnBook) {
                        text = date.date.format(formatter)
                        isEnabled = true
                        setBackgroundColor(resources.getColor(com.hs.workation.core.common.R.color.grey_900, null))
                    }
                }
            }
        }
    }

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
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
//        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
        try {
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if(newState == BottomSheetBehavior.STATE_HIDDEN) {
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
