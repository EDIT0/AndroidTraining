package com.example.bottomsheetdialogexample1

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetDialog1Fragment : com.google.android.material.bottomsheet.BottomSheetDialogFragment() {

    private val TAG = BottomSheetDialog1Fragment::class.java.simpleName

    private var bottomSheetView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bottomSheetView = inflater.inflate(R.layout.fragment_bottom_sheet_dialog1, container, false)
        return bottomSheetView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(bottomSheetView?.context, "${arguments?.getString("dataString")}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.skipCollapsed = true

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.i(TAG, "STATE_HIDDEN")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.i(TAG, "STATE_EXPANDED")
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Log.i(TAG, "STATE_HALF_EXPANDED")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.i(TAG, "STATE_COLLAPSED")
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.i(TAG, "STATE_DRAGGING")
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        Log.i(TAG, "STATE_SETTLING")
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 90 / 100
        // 기기 높이 대비 비율 설정 부분!!
        // 위 수치는 기기 높이 대비 80%로 다이얼로그 높이를 설정
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}