package com.example.bottomsheetdialogexample1

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomsheetdialogexample1.databinding.FragmentBottomSheetDialog1Binding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//class BottomSheetDialogFragment1 : BottomSheetDialogFragment() {
//
//    private val TAG = BottomSheetDialogFragment1::class.java.simpleName
//
//    private var _binding: FragmentBottomSheetDialog1Binding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentBottomSheetDialog1Binding.inflate(inflater, container, false)
//        val view = binding.root
//        return view
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
//        dialog.setOnShowListener {
//            val bottomSheetDialog = it as BottomSheetDialog
//            // 다이얼로그 크기 설정 (인자값 : DialogInterface)
//            setupRatio(bottomSheetDialog)
//            Log.i(TAG, "다이얼로그 생성")
//        }
//        return dialog
//    }
//
//    /** setupRatio()
//     * bottomSheet
//     *      - 전달받은 DialogInterface를 통해 View를 참조하도록 한다.
//     *      - 이때 id 값에는 내가 만든 View가 들어가는게 아님을 주의하자.
//     * layoutParams
//     *      - View를 감싸고 있는 Parents에게 어떻게 레이아웃 할 것인지 설정하는데 사용함.
//     * behavior
//     *      - 생성한 View를 통해 BottomSheetBehavior를 생성한다.
//     *      - 역할 : BottomSheetBehavior로 상태값을 '확장형'으로 설정해준다.
//     * */
//    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
//        //id = com.google.android.material.R.id.design_bottom_sheet for Material Components
//        //id = android.support.design.R.id.design_bottom_sheet for support librares
//        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
//        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
//
//
//        // 드래그해도 팝업이 종료되지 않도록
//        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
//                    Log.i(TAG, "STATE_DRAGGING")
//                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                }
//
//                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                    Log.i(TAG, "STATE_EXPANDED")
//                    binding.expandedLayout.visibility = View.VISIBLE
//                    binding.tvCollapsedTitle.visibility = View.INVISIBLE
//                } else if(newState == BottomSheetBehavior.STATE_COLLAPSED) {
//                    Log.i(TAG, "STATE_COLLAPSED")
//                    binding.expandedLayout.visibility = View.INVISIBLE
//                    binding.tvCollapsedTitle.visibility = View.VISIBLE
//                }
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                // slideOffset 접힘 -> 펼쳐짐: 0.0 ~ 1.0
//                if (slideOffset >= 0) {
//                    // 둥글기는 펼칠수록 줄어들도록
////                    binding.bottomSheet.radius = cornerRadius - (cornerRadius * slideOffset)
//                    // 화살표는 완전히 펼치면 180도 돌아가게
////                    binding.guideline1.rotation =  (1 - slideOffset) * 180F
//                    // 글자는 조금더 빨리 사라지도록
//                    binding.tvCollapsedTitle.alpha = 1 - slideOffset * 2.3F
//                    // 내용의 투명도도 같이 조절...
//                    binding.expandedLayout.alpha = Math.min(slideOffset * 2F, 1F)
//                }
//            }
//        })
//
////        val layoutParams = bottomSheet!!.layoutParams
////        layoutParams.height = getBottomSheetDialogDefaultHeight()
////        bottomSheet.layoutParams = layoutParams
////        behavior.state = BottomSheetBehavior.STATE_EXPANDED
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        // 팝업 생성 시 전체화면으로 띄우기
////        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
////        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
//
////        // 드래그해도 팝업이 종료되지 않도록
////        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
////            override fun onStateChanged(bottomSheet: View, newState: Int) {
////                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
////                    Log.i(TAG, "STATE_DRAGGING")
////                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
////                }
////
////                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
////                    Log.i(TAG, "STATE_EXPANDED")
////                    binding.expandedLayout.visibility = View.VISIBLE
////                    binding.tvCollapsedTitle.visibility = View.INVISIBLE
////                } else if(newState == BottomSheetBehavior.STATE_COLLAPSED) {
////                    Log.i(TAG, "STATE_COLLAPSED")
////                    binding.expandedLayout.visibility = View.INVISIBLE
////                    binding.tvCollapsedTitle.visibility = View.VISIBLE
////                }
////            }
////
////            override fun onSlide(bottomSheet: View, slideOffset: Float) {
////                // slideOffset 접힘 -> 펼쳐짐: 0.0 ~ 1.0
////                if (slideOffset >= 0) {
////                    // 둥글기는 펼칠수록 줄어들도록
//////                    binding.bottomSheet.radius = cornerRadius - (cornerRadius * slideOffset)
////                    // 화살표는 완전히 펼치면 180도 돌아가게
//////                    binding.guideline1.rotation =  (1 - slideOffset) * 180F
////                    // 글자는 조금더 빨리 사라지도록
////                    binding.tvCollapsedTitle.alpha = 1 - slideOffset * 2.3F
////                    // 내용의 투명도도 같이 조절...
////                    binding.expandedLayout.alpha = Math.min(slideOffset * 2F, 1F)
////                }
////            }
////        })
//    }
//}