package com.example.bottomsheetdialogexample1

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.bottomsheetdialogexample1.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bottomSheet: View = binding.includeBottomSheet.bottomSheetLayout
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<View>(bottomSheet)
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.halfExpandedRatio = 0.25f

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.i(TAG, "STATE_HIDDEN")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.i(TAG, "STATE_EXPANDED")
                        binding.includeBottomSheet.apply {
                            expandedLayout.visibility = View.VISIBLE
                            tvCollapsedTitle.visibility = View.GONE
                            tvHalfExpandedTitle.visibility = View.GONE
                            tvHalfExpandedSubTitle.visibility = View.GONE
                        }
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Log.i(TAG, "STATE_HALF_EXPANDED")
                        binding.includeBottomSheet.apply {
                            expandedLayout.visibility = View.GONE
                            tvCollapsedTitle.visibility = View.VISIBLE
                            tvHalfExpandedTitle.visibility = View.VISIBLE
                            tvHalfExpandedSubTitle.visibility = View.VISIBLE
                        }
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.i(TAG, "STATE_COLLAPSED")
                        binding.includeBottomSheet.apply {
                            expandedLayout.visibility = View.GONE
                            tvCollapsedTitle.visibility = View.VISIBLE
                            tvHalfExpandedTitle.visibility = View.VISIBLE
                            tvHalfExpandedSubTitle.visibility = View.VISIBLE
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