package com.example.statusbarcontroldemo1.base

import android.view.View
import androidx.fragment.app.Fragment
import com.example.statusbarcontroldemo1.HomeFragment
import com.example.statusbarcontroldemo1.MyPageFragment
import com.example.statusbarcontroldemo1.Util

open class BaseFragment : Fragment() {

    private val whiteStatusBar : Int = 0
    private val blackStatusBar : Int = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    fun setStatusBarIconColorWhite() {
        if(Util.getStatusBarIconColor(requireActivity()) != whiteStatusBar) {
            requireActivity().window.decorView.systemUiVisibility = whiteStatusBar // 상태바 아이콘 흰색
        }
    }

    fun setStatusBarIconColorBlack() {
        if(Util.getStatusBarIconColor(requireActivity()) != blackStatusBar) {
            requireActivity().window.decorView.systemUiVisibility = blackStatusBar // 상태바 아이콘 검은색
        }
    }

    override fun onResume() {
        super.onResume()

        when(javaClass) {
            HomeFragment::class.java -> {
                setStatusBarIconColorWhite()
            }
            MyPageFragment::class.java -> {
                setStatusBarIconColorBlack()
            }
            else -> {

            }
        }
    }

    override fun onStop() {
        super.onStop()

        when(javaClass) {
            HomeFragment::class.java -> {
                setStatusBarIconColorWhite()
            }
            MyPageFragment::class.java -> {
                setStatusBarIconColorBlack()
            }
            else -> {

            }
        }
    }
}