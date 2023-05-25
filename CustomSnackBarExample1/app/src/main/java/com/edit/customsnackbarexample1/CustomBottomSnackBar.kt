package com.edit.customsnackbarexample1

import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.edit.customsnackbarexample1.databinding.CustomBottomSnackBarBinding
import com.edit.customsnackbarexample1.databinding.CustomTopSnackBarBinding
import com.google.android.material.snackbar.Snackbar

class CustomBottomSnackBar(
    private val view: View,
    private val message: String,
    private val time: Long = 0L
) {
    private var snackBar: Snackbar? = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
    private var snackBarLayout: Snackbar.SnackbarLayout? = snackBar?.view as Snackbar.SnackbarLayout
    private lateinit var customBottomSnackBarBinding: CustomBottomSnackBarBinding

    private var showAnim: Animation = AnimationUtils.loadAnimation(view.context, R.anim.bottom_to_top_from_bottom)
    private var hideAnim: Animation = AnimationUtils.loadAnimation(view.context, R.anim.top_to_bottom_from_bottom)

    init {
        val inflater = LayoutInflater.from(view.context)
        customBottomSnackBarBinding = CustomBottomSnackBarBinding.inflate(inflater)
        initData()
        buttonClickListener()
    }

    private fun initData() {
        customBottomSnackBarBinding.tvMessage.text = message
    }

    private fun buttonClickListener() {
        customBottomSnackBarBinding.btnClose.setOnClickListener {
            snackBarLayout?.startAnimation(hideAnim)
            android.os.Handler(Looper.getMainLooper())
                .postDelayed(
                    Runnable {
                        snackBar?.dismiss()
                    }, 700)
        }
        customBottomSnackBarBinding.btnGoToEvent.setOnClickListener {
            Toast.makeText(view.context, "이벤트 이동", Toast.LENGTH_SHORT).show()
            snackBarLayout?.startAnimation(hideAnim)
            android.os.Handler(Looper.getMainLooper())
                .postDelayed(
                    Runnable {
                        snackBar?.dismiss()
                    }, 700)
        }
    }

    fun show() {
        snackBarLayout?.let {
            it.startAnimation(showAnim)

            if(0L == time) {
            } else {
                android.os.Handler(Looper.getMainLooper())
                    .postDelayed(
                        Runnable {
                            it.startAnimation(hideAnim)
                            android.os.Handler(Looper.getMainLooper())
                                .postDelayed(
                                    Runnable {
                                        snackBar?.dismiss()
                                    }, 700)
                        }, time)
            }


            val layoutParams = it.layoutParams as FrameLayout.LayoutParams
            layoutParams.gravity = Gravity.BOTTOM
            it.removeAllViews()
            it.setPadding(0, 0, 0, 0)
            it.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
            it.addView(customBottomSnackBarBinding.root, 0)
        }

        snackBar?.show()
    }

}