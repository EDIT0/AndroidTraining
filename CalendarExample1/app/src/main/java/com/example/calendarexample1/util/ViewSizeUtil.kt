package com.example.calendarexample1.util

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView

object ViewSizeUtil {

    fun dpToPx(context: Context, dp: Float): Double {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toDouble()
    }

    fun pxToDp(context: Context, px: Float): Double {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toDouble()
    }

    fun getDeviceWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
//            windowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun getDeviceHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.bottom - insets.top
//            windowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    fun getDeviceScreenWidth(activity: Activity): Int {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        return size.x
    }

    fun getDeviceScreenHeight(activity: Activity): Int {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        return size.y
    }

    fun computeDistanceToViewForJava(scrollView: ScrollView, view: View): Int {
        return scrollView.computeDistanceToView(view)
    }

    fun ScrollView.computeDistanceToView(view: View): Int {
        return Math.abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
    }

    fun computeDistanceToViewForJava(scrollView: NestedScrollView, view: View): Int {
        return scrollView.computeDistanceToView(view)
    }

    fun NestedScrollView.computeDistanceToView(view: View): Int {
        return Math.abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
    }

    private fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(location[0], location[1], location[0] + view.measuredWidth, location[1] + view.measuredHeight)
    }

}