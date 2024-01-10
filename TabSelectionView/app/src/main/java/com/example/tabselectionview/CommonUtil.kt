package com.example.tabselectionview

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Point
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


object CommonUtil {

    var toast: Toast? = null

    fun showToast(context: Context, msg: String?) {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        toast?.show()
    }

    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun pxToDp(context: Context, px: Int): Int {
        val resources: Resources = context.resources
        val metrics: DisplayMetrics = resources.getDisplayMetrics()
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    fun getDeviceWidth(activity: Activity): Int {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)

        return size.x
    }

    fun getDeviceHeight(activity: Activity): Int {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)

        return size.y
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

    fun RecyclerView.anchorSmoothScrollToPosition(position: Int, anchorPosition: Int = 3) {
        layoutManager?.apply {
            when (this) {
                is LinearLayoutManager -> {
                    val topItem = findFirstVisibleItemPosition()
                    val distance = topItem - position
                    val anchorItem = when {
                        distance > anchorPosition -> position + anchorPosition
                        distance < -anchorPosition -> position - anchorPosition
                        else -> topItem
                    }
                    if (anchorItem != topItem) scrollToPosition(anchorItem)
                    post {
                        scrollToPosition(position)
                    }
                }
                else -> scrollToPosition(position)
            }
        }
    }

    fun Activity.setStatusBarTransparent() {
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    fun Activity.setStatusBarOrigin() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    }

    fun Context.statusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else 0
    }

    fun Context.navigationHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else 0
    }

    fun getColorSelectedStateList(context: Context, selected: Int, unselected: Int): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_selected),
            intArrayOf(-android.R.attr.state_selected)
        )
        val colors = intArrayOf(
            // selected color
            ContextCompat.getColor(context, selected),
            // unselected color
            ContextCompat.getColor(context, unselected)
        )

        return ColorStateList(states, colors)
    }

    fun getDrawableSelectedStateList(context: Context, selected: Int, unselected: Int): StateListDrawable {
        val pressedTabDrawable = ContextCompat.getDrawable(context, selected)
        val normalTabDrawable = ContextCompat.getDrawable(context, unselected)
        val stateListTabDrawable = StateListDrawable()
        stateListTabDrawable.addState(intArrayOf(android.R.attr.state_selected), pressedTabDrawable)
        stateListTabDrawable.addState(intArrayOf(), normalTabDrawable)
        return stateListTabDrawable
    }
}