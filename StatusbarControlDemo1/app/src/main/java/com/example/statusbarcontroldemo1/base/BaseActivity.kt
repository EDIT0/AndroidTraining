package com.example.statusbarcontroldemo1.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun applyPaddingToContentView(view: View, start: Float, top: Float, end: Float, bottom: Float) {
        view.setPadding(start.toInt(), top.toInt(), end.toInt(), bottom.toInt())
    }

    protected fun setActivityPadding(view: View, start: Float, top: Float, end: Float, bottom: Float) {
        applyPaddingToContentView(view, start, top, end, bottom)
    }

    private fun applyMarginToContentView(view: View, start: Float, top: Float, end: Float, bottom: Float) {
        val layoutParams = view.layoutParams as? ViewGroup.MarginLayoutParams
        layoutParams?.setMargins(start.toInt(), top.toInt(), end.toInt(), bottom.toInt())
        view.layoutParams = layoutParams
    }

    protected fun setActivityMargin(view: View, start: Float, top: Float, end: Float, bottom: Float) {
        applyMarginToContentView(view, start, top, end, bottom)
    }
}