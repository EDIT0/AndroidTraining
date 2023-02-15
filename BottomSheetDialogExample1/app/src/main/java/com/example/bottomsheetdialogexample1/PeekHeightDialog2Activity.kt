package com.example.bottomsheetdialogexample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.bottomsheetdialogexample1.databinding.ActivityPeekHeightDialog2Binding
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * Expanded: fragment를 보여준다.
 * Collapsed: fragment를 제외한 화면만 보여준다.
 * */
class PeekHeightDialog2Activity : AppCompatActivity() {

    private val TAG = PeekHeightDialog2Activity::class.java.simpleName

    private lateinit var binding: ActivityPeekHeightDialog2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeekHeightDialog2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bottomSheet: View = binding.bottomSheetLayout
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from<View>(bottomSheet)
        // true로 하면 Expanded 아니면 Collapsed 임
//        bottomSheetBehavior.isFitToContents = false
        // 중간 상태 설정 (0 ~ 1)
//        bottomSheetBehavior.halfExpandedRatio = 0.25f
        // Expanded 되었을 때 위에서부터 얼마나 띄울건지
//        bottomSheetBehavior.expandedOffset = 100
        // 특정 상태로 설정
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.i(TAG, "STATE_HIDDEN")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.i(TAG, "STATE_EXPANDED")
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment, RandomNumberFragment.newInstance("", ""))
                            .commit()

                        binding.apply {
                            tvCollapsedTitle.visibility = View.GONE
                            fragment.visibility = View.VISIBLE
                        }
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Log.i(TAG, "STATE_HALF_EXPANDED")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.i(TAG, "STATE_COLLAPSED")
                        binding.apply {
                            tvCollapsedTitle.visibility = View.VISIBLE
                            fragment.visibility = View.GONE
                        }
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

    }
}