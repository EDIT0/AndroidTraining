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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.workation.core.component.databinding.NumberPickerBottomSheetBinding
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import com.hs.workation.core.resource.R

/**
 * 숫자 선택 바텀 시트
 *
 * @property title : 타이틀
 * @property peopleCount : 선택된 값
 * @property resultCallback : 버튼 클릭 시 콜백
 */
class CommonNumberPickerSheet(
    private val title: String? = null,
    private val peopleCount: Int? = null,
    private val resultCallback: (Int) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: NumberPickerBottomSheetBinding

    private lateinit var behavior: BottomSheetBehavior<*>

    // 뷰에서 사용할 데이터 sharedFlow 사용
    private val _people = MutableSharedFlow<Int>()
    val people = _people.asSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NumberPickerBottomSheetBinding.inflate(inflater)

        setTitle()
        setConfirmButton()
        initNumberPicker()

        return binding.root
    }

    /** 타이틀 세팅 */
    private fun setTitle() {
        binding.title.text = title ?: ""
    }

    /** 커스텀 버튼 동작 */
    private fun setConfirmButton() {
        with(binding.btnConfirm) {
            setOnClickListener {
                dismiss()
            }
        }
    }

    /** 넘버 피커 초기화 */
    private fun initNumberPicker() {
        with (binding.numberPicker) {
            minValue = 0 // 최솟값 설정
            maxValue = 100 // 최댓값 설정
            value = peopleCount ?: 0

            setOnValueChangedListener { picker, oldVal, newVal ->
                resultCallback(newVal)
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
