package com.hs.workation.core.component

import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object CommonToast {

    private var snackBar : Snackbar? = null

    fun makeToast(view: View, message: String) {
        snackBar?.dismiss()
        snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)

        val params = snackBar?.view?.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER
        snackBar?.view?.layoutParams = params

        snackBar?.let {
            val background = GradientDrawable()
            background.shape = GradientDrawable.RECTANGLE
            background.cornerRadius = 20f
            background.setColor(ContextCompat.getColor(view.context, com.hs.workation.core.common.R.color.blackAlpha60))
            it.view.background = background

            val snackBarTextView = it.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            snackBarTextView.maxLines = Int.MAX_VALUE
            snackBarTextView.gravity = Gravity.CENTER
            snackBarTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackBarTextView.textSize = 16f
            snackBarTextView.setTextColor(ContextCompat.getColor(view.context, com.hs.workation.core.common.R.color.white))

            it.show()
        }
    }
}