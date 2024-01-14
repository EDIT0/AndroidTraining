package com.example.statusbarcontroldemo1

import android.os.Bundle
import com.example.statusbarcontroldemo1.Util.navigationHeight
import com.example.statusbarcontroldemo1.Util.setLightNavigationBarIcons
import com.example.statusbarcontroldemo1.Util.setStatusBarTransparent
import com.example.statusbarcontroldemo1.base.BaseActivity
import com.example.statusbarcontroldemo1.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingTitleNavStatusBar()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            replace(R.id.fragment_container, homeFragment, homeFragment.tag)
            commit()
        }

    }

    private fun settingTitleNavStatusBar() {
        // 상태 바 투명으로 설정(전체화면)
        this.setStatusBarTransparent()
        // 하단 네비게이션 바를 침범하지 않도록 설정
        setActivityPadding(binding.root, 0f, 0f, 0f, this.navigationHeight().toFloat())
        // 하단 네비게이션 바 margin 설정
        setActivityMargin(binding.root, 0f, 0f, 0f, 0f)
        // 네비게이션 색깔 흰색으로 설정
        setLightNavigationBarIcons()
    }
}