package com.hs.workation.core.component

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.workation.core.component.databinding.SelectBottomSheetBinding

/**
 * 선택 목록 바텀 시트
 *
 * @property title : 타이틀
 * @property list : 선택 목록
 * @property resultCallback : 버튼 클릭 시 콜백
 */
class CommonSelectSheet(
    private val title: String? = null,
    private val list: List<String>,
    private val resultCallback: (String) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: SelectBottomSheetBinding

    private lateinit var behavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SelectBottomSheetBinding.inflate(inflater)

        setTitle(binding.title)
        setCloseButton(binding.btnClose)
        initSelectList(binding.cvSelectList)

        return binding.root
    }

    /** 타이틀 세팅 */
    private fun setTitle(title: TextView) {
        title.text = this.title ?: ""
    }

    /** 닫기 버튼 동작 */
    private fun setCloseButton(btnClose: ImageButton) {
        btnClose.setOnClickListener {
            dismiss()
        }
    }

    /** 선택 목록 초기화 */
    private fun initSelectList(cvSelectList: ComposeView) {
        cvSelectList.setContent {
            val scrollState = rememberScrollState()
            val connection = rememberNestedScrollInteropConnection()

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .nestedScroll(connection = connection)
            ) {
                list.map {
                    ListItem(
                        headlineContent = {
                            Text(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                dismiss()
                                resultCallback(it)
                            },
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent
                        )
                    )
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
