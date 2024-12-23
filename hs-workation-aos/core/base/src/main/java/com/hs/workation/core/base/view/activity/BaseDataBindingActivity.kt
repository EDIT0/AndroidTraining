package com.hs.workation.core.base.view.activity

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hs.workation.core.common.navigation.NavActivity
import com.hs.workation.core.component.CommonToast
import com.hs.workation.core.util.navigationHeight
import javax.inject.Inject

open class BaseDataBindingActivity<T : ViewDataBinding>(@LayoutRes val layoutResId: Int): AppCompatActivity() {

    @Inject
    lateinit var navActivity: NavActivity

    private var _binding: T? = null
    val binding get() = _binding!!

    // Activity
    lateinit var activity: Activity

    // Toast
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingDataBinding()
    }

    private fun settingDataBinding() {
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
        activity = this
    }

    open fun showToast(message: String?) {
        CommonToast.makeToast(binding.root, message?:"")
    }

    open fun setStatusNavigationBar(view: View, isNaviIconBlack: Boolean) {
        // Status, Navigation Bar 열기
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if (Build.VERSION.SDK_INT >= 30) {    // API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }

        // 내비게이션바 아이콘 색상
        val wic = WindowInsetsControllerCompat(window, window.decorView)
//        wic.isAppearanceLightStatusBars = isIconBlack // true: 어두운 아이콘, false: 밝은 아이콘
        wic.isAppearanceLightNavigationBars = isNaviIconBlack // true: 어두운 아이콘, false: 밝은 아이콘

        // Navigation Bar 배경색은 Activity 배경색과 동일
        view.setPadding(0, 0, 0, navigationHeight())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}