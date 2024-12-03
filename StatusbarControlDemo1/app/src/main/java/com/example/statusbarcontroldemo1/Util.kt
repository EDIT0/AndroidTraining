package com.example.statusbarcontroldemo1

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

object Util {
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

    fun Activity.setLightNavigationBarIcons() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                )

        if (Build.VERSION.SDK_INT >= 30) {
            // API 30 이상에서는 WindowCompat을 이용하여 내비게이션 바 투명 설정
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

    fun getStatusBarIconColor(mActivity: Activity): Int {
        return mActivity.window.statusBarColor
    }

    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    fun pxToDp(context: Context, px: Int): Int {
        val resources: Resources = context.resources
        val metrics: DisplayMetrics = resources.getDisplayMetrics()
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    /**
     * 상단 상태바, 하단 내비게이션바 설정
     * 상단 상태바는 영역 사용 O, 하단 내비게이션바는 영역 사용은 isNaviBlock 값에 따라 유동적
     *
     * @param activity
     * @param view
     * @param isNaviBlock 하단 내비게이션바 true: show, false: hide
     */
    fun setStatusNavigationBar(activity: Activity, isNaviBlock: Boolean) {
        // Status, Navigation Bar 열기
        if(isNaviBlock) {
            activity.apply {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = 0x00000000  // transparent

                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        } else {
            activity.window.apply {
                setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
        }

        if (Build.VERSION.SDK_INT >= 30) {    // API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            activity.window.decorView.setOnApplyWindowInsetsListener { view, insets ->
//                val systemBarsInsets = insets.systemWindowInsets
//                view.setPadding(0, 0, 0, systemBarsInsets.bottom)
//                insets
//            }
//        } else {
//            activity.window.decorView.setOnApplyWindowInsetsListener { view, insets ->
//                // 상태 바와 내비게이션 바의 인셋을 가져옵니다.
//                view.setPadding(0, 0, 0, activity.navigationHeight())
//                insets
//            }
//        }
    }

    /**
     * 상단 상태바 설정 (Padding, Icon color)
     *
     * @param activity
     * @param view
     * @param isStatusBarPadding 상태바 사용 true, 사용하지 않음 false
     * @param isNavigationBarPadding
     * @param isStatusIconBlack 상태바 아이콘 ture: Black
     * @param isNaviIconBlack
     */
    fun setStatusNaviBarPadding(
        activity: Activity,
        view: View,
        isStatusBarPadding: Boolean,
        isNavigationBarPadding: Boolean,
        isStatusIconBlack: Boolean,
        isNaviIconBlack: Boolean
    ) {
        // 상태바 아이콘 색상
        val wic = WindowInsetsControllerCompat(activity.window, activity.window.decorView)
        wic.isAppearanceLightStatusBars = isStatusIconBlack // true: 어두운 아이콘, false: 밝은 아이콘
        wic.isAppearanceLightNavigationBars = isNaviIconBlack // true: 어두운 아이콘, false: 밝은 아이콘

        if (Build.VERSION.SDK_INT >= 30) {
            if(isStatusBarPadding && isNavigationBarPadding) {
                view.setPadding(0, activity.statusBarHeight(), 0, activity.navigationHeight())
            } else if(isStatusBarPadding) {
                view.setPadding(0, activity.statusBarHeight(), 0, 0)
            } else if(isNavigationBarPadding) {
                view.setPadding(0, 0, 0, activity.navigationHeight())
            } else {
                view.setPadding(0, 0, 0, 0)
            }
        } else {
            if(isStatusBarPadding && isNavigationBarPadding) {
                view.setPadding(0, activity.statusBarHeight(), 0, 0)
            } else if(isStatusBarPadding) {
                view.setPadding(0, activity.statusBarHeight(), 0, 0)
            } else if(isNavigationBarPadding) {
                view.setPadding(0, 0, 0, 0)
            } else {
                view.setPadding(0, 0, 0, 0)
            }
        }

    }
}