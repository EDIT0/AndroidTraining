package com.example.coordinatorlayoutdemo5

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.example.coordinatorlayoutdemo5.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tabLayoutSetting()
        nestedScrollViewSetting()
        appBarLayoutListener()
    }

    fun NestedScrollView.computeDistanceToView(view: View): Int {
        return Math.abs(calculateRectOnScreen(this).top - (this.scrollY + calculateRectOnScreen(view).top))
    }

    fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(location[0], location[1], location[0] + view.measuredWidth, location[1] + view.measuredHeight)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun nestedScrollViewSetting() {
        binding.nestedScrollview.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(p0: View?, p1: Int, scrollY: Int, p3: Int, p4: Int) {
                when {
                    scrollY == 0 -> {

                    }
                    scrollY == binding.nestedScrollview.computeDistanceToView(binding.ivImg) -> {

                    }
                    scrollY >= binding.nestedScrollview.computeDistanceToView(binding.ivImg) && scrollY < binding.nestedScrollview.computeDistanceToView(binding.tv1) -> {
                        binding.actionBarLayout1.visibility = View.GONE
                    }
                    scrollY >= binding.nestedScrollview.computeDistanceToView(binding.tv1) && scrollY < binding.nestedScrollview.computeDistanceToView(binding.tv2) -> {
                        binding.actionBarLayout1.visibility = View.VISIBLE

                        binding.tablayout.setScrollPosition(1, 0f, true)
                    }
                    scrollY >= binding.nestedScrollview.computeDistanceToView(binding.tv2) && scrollY < binding.nestedScrollview.computeDistanceToView(binding.tv3) -> {
                        binding.actionBarLayout2.visibility = View.GONE

                        binding.tablayout.setScrollPosition(2, 0f, true)
                    }

                    scrollY >= binding.nestedScrollview.computeDistanceToView(binding.tv3) && scrollY < binding.nestedScrollview.computeDistanceToView(binding.tv4) ->  {
                        binding.actionBarLayout2.visibility = View.VISIBLE

                        binding.tablayout.setScrollPosition(3, 0f, true)
                    }
                    scrollY >= binding.nestedScrollview.computeDistanceToView(binding.tv4) && scrollY < binding.nestedScrollview.computeDistanceToView(binding.tv5) -> {
                        binding.tablayout.setScrollPosition(4, 0f, true)
                    }

                    scrollY >= binding.nestedScrollview.computeDistanceToView(binding.tv5) && scrollY < binding.nestedScrollview.computeDistanceToView(binding.tv6) ->  {
                        binding.tablayout.setScrollPosition(5, 0f, true)
                    }
                }

                if(!binding.nestedScrollview.canScrollVertically(1)) {
                    binding.tablayout.setScrollPosition(6, 0f, true)
                }
            }

        })
    }

    private fun appBarLayoutListener() {
        binding.appbar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                //  Collapsed
                Log.i("MYTAG", "Collapsed")
                binding.collapsingToolbarLayout.title = "Hello Title"
                Glide.with(binding.ivMaterialToolbarImage1.context)
                    .load(R.drawable.ic_baseline_arrow_back_ios_new_24_black)
                    .into(binding.ivMaterialToolbarImage1)
            } else {
                //Expanded
                Log.i("MYTAG", "Expanded")
                binding.collapsingToolbarLayout.title = ""
                Glide.with(binding.ivMaterialToolbarImage1.context)
                    .load(R.drawable.ic_baseline_arrow_back_ios_new_24)
                    .into(binding.ivMaterialToolbarImage1)
            }
        })
    }

    private fun tabLayoutSetting() {
        val tab_mainFragment = binding.tablayout.newTab().setText("전체")
        binding.tablayout.addTab(tab_mainFragment)
        val tab_mainSounddoodleListFragment = binding.tablayout.newTab().setText("1번 탭")
        binding.tablayout.addTab(tab_mainSounddoodleListFragment)
        val tab_mainDoodleListFragment = binding.tablayout.newTab().setText("2번 탭")
        binding.tablayout.addTab(tab_mainDoodleListFragment)
        val tab_mainDoodleListFragment3 = binding.tablayout.newTab().setText("3번 탭")
        binding.tablayout.addTab(tab_mainDoodleListFragment3)
        val tab_mainDoodleListFragment4 = binding.tablayout.newTab().setText("4번 탭")
        binding.tablayout.addTab(tab_mainDoodleListFragment4)
        val tab_mainDoodleListFragment5 = binding.tablayout.newTab().setText("5번 탭")
        binding.tablayout.addTab(tab_mainDoodleListFragment5)
        val tab_mainDoodleListFragment6 = binding.tablayout.newTab().setText("6번 탭")
        binding.tablayout.addTab(tab_mainDoodleListFragment6)

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 선택되지 않았던 탭이 선택되었을 경우

                binding.appbar.setExpanded(false, true)
                when (tab?.position) {
                    0 -> binding.nestedScrollview.smoothScrollToView(binding.ivImg) //전체
                    1 -> binding.nestedScrollview.smoothScrollToView(binding.tv1) //예적금
                    2 -> binding.nestedScrollview.smoothScrollToView(binding.tv2) //대출
                    3 -> binding.nestedScrollview.smoothScrollToView(binding.tv3) //서비스
                    4 -> binding.nestedScrollview.smoothScrollToView(binding.tv4) //제휴
                    5 -> binding.nestedScrollview.smoothScrollToView(binding.tv5) //mini
                    6 -> binding.nestedScrollview.smoothScrollToView(binding.tv6) //mini
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택된 상태에서 선택되지 않음으로 변경될 경우
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택되었을 경우
                binding.appbar.setExpanded(false, true)
                when (tab?.position) {
                    0 -> binding.nestedScrollview.smoothScrollToView(binding.ivImg) //전체
                    1 -> binding.nestedScrollview.smoothScrollToView(binding.tv1) //예적금
                    2 -> binding.nestedScrollview.smoothScrollToView(binding.tv2) //대출
                    3 -> binding.nestedScrollview.smoothScrollToView(binding.tv3) //서비스
                    4 -> binding.nestedScrollview.smoothScrollToView(binding.tv4) //제휴
                    5 -> binding.nestedScrollview.smoothScrollToView(binding.tv5) //mini
                    6 -> binding.nestedScrollview.smoothScrollToView(binding.tv6) //mini
                }
            }
        })
    }

    fun NestedScrollView.smoothScrollToView(
        view: View,
        marginTop: Int = 0,
        maxDuration: Long = 500L,
        onEnd: () -> Unit = {}
    ) {
        if (this.getChildAt(0).height <= this.height) { // 스크롤의 의미가 없다.
            onEnd()
            return
        }
        val y = computeDistanceToView(view) - marginTop
        val ratio = Math.abs(y - this.scrollY) / (this.getChildAt(0).height - this.height).toFloat()
        ObjectAnimator.ofInt(this, "scrollY", y).apply {
            duration = (maxDuration * ratio).toLong()
            interpolator = AccelerateDecelerateInterpolator()
            doOnEnd {
                onEnd()
            }
            start()
        }
    }
}