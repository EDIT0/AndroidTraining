package com.example.tabselectionview.set

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tabselectionview.CommonUtil
import com.example.tabselectionview.R
import com.example.tabselectionview.databinding.SelectionTabViewBinding
import com.google.android.material.tabs.TabLayout

class SelectionTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), BaseSelectionTabView {

    private var binding: SelectionTabViewBinding = SelectionTabViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var currentTab = Integer.MIN_VALUE

    private var eachTabList = ArrayList<TabLayout.Tab>()
    private var tabTextList = ArrayList<TextView>()

    // 사용자 설정
    private var tabList = ArrayList<String>()
    private var tabLayoutHeight: Int = 30
    private var tabLayoutShape: Int = R.drawable.tab_layout_shape_1
    private var tabIndicatorShape: Int = R.drawable.tab_indicator_shape_1
    private var tabIndicatorColor: Int = R.color.blue
    private var selectedTabTextColor: Int = R.color.white
    private var unselectedTabTextColor: Int = R.color.black
    private var tabBackground: Int? = null
    private var isWrapContent: Boolean = false


    override fun setTabLayoutHeight(heightDp: Int) {
        tabLayoutHeight = heightDp
    }

    override fun setTabLayoutBackground(tabLayoutBg: Int) {
        tabLayoutShape = tabLayoutBg
    }

    override fun setTabIndicatorShape(indicatorBg: Int) {
        tabIndicatorShape = indicatorBg
    }

    override fun setTabIndicatorColor(indicatorColor: Int) {
        tabIndicatorColor = indicatorColor
    }

    override fun setTabTextColor(selectedTabColor: Int, unselectedTabColor: Int) {
        selectedTabTextColor = selectedTabColor
        unselectedTabTextColor = unselectedTabColor
    }

    override fun setTabBackground(tabBg: Int?) {
        tabBackground = tabBg
    }

    /**
     * View의 width가 wrap_content 또는 길이가 고정일 때 (100dp, 200dp 등): true
     * View의 width가 match_parent 또는 0dp 일 때: false
     * */
    override fun setIsWrapContent(isWrapContent: Boolean) {
        this.isWrapContent = isWrapContent
    }

    override fun setList(list: List<String>) {
        tabList.clear()
        tabList = list as ArrayList

        tabListener()

        // TabLayout 높이 설정
        val tabLayoutParams = binding.tabLayout.layoutParams
        tabLayoutParams.height = CommonUtil.dpToPx(context, tabLayoutHeight.toFloat())
        binding.tabLayout.layoutParams = tabLayoutParams

        // Indicator 높이 설정
        binding.tabLayout.setSelectedTabIndicatorHeight(CommonUtil.dpToPx(context, tabLayoutHeight.toFloat()))

        // indicator 색깔 설정
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context, tabIndicatorColor))
//        binding.tabLayout.setSelectedTabIndicator(R.drawable.tab_stroke_selected)

        // tabLayout background drawable
        binding.rootLayout.setBackgroundResource(tabLayoutShape)

        // selected tab background drawable
        binding.tabLayout.setSelectedTabIndicator(tabIndicatorShape)

//        val gradientDrawable = GradientDrawable()
//        val shapeDrawable = GradientDrawable()
//        if(tabIndicatorShape == ROUND) {
//            // tabIndicatorShape 값에 따라 TabLayout을 감싸는 레이아웃도 모양을 설정해준다.
//            gradientDrawable.shape = GradientDrawable.RECTANGLE
//            gradientDrawable.cornerRadius = CommonUtil.dpToPx(context, tabIndicatorRadius).toFloat()
////            gradientDrawable.setColor(0xFF0000FF.toInt())
//
//            // Indicator 모양 설정
//            shapeDrawable.shape = GradientDrawable.RECTANGLE
//            shapeDrawable.cornerRadius = CommonUtil.dpToPx(context, tabIndicatorRadius).toFloat()
////            shapeDrawable.setColor(ContextCompat.getColor(context, R.color.blue)) // 색깔은 적용되지 않음
//
//        } else if(tabIndicatorShape == RECT) {
//            // tabIndicatorShape 값에 따라 TabLayout을 감싸는 레이아웃도 모양을 설정해준다.
//            gradientDrawable.shape = GradientDrawable.RECTANGLE
////            gradientDrawable.setColor(0xFF0000FF.toInt())
//
//            // Indicator 모양 설정
//            shapeDrawable.shape = GradientDrawable.RECTANGLE
////            shapeDrawable.setColor(ContextCompat.getColor(context, R.color.blue)) // 색깔은 적용되지 않음
//        }
//        binding.rootLayout.setBackgroundResource(tabLayoutBackground)
//        binding.rootLayout.setBackgroundDrawable(gradientDrawable)
//        binding.rootLayout.setBackground(gradientDrawable)
//        binding.rootLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.pink))
//        binding.tabLayout.background = shapeDrawable


        for(i in 0 until list.size) {
            val tab = binding.tabLayout.newTab()

            tab.apply {
                tag = i
                tabBackground?.let {
                    view.setBackgroundResource(it)
                }
//                background = ContextCompat.getDrawable(context, R.drawable.tab_selection_background)
                customView = createTabView()
                val tvTabTitle: TextView = customView!!.findViewById<TextView>(R.id.tvTabTitle)
                tvTabTitle?.let {
                    // Tab 타이틀 설정
                    it.text = tabList[i]
                    // Tab 텍스트 색 설정
                    it.setTextColor(CommonUtil.getColorSelectedStateList(context, selectedTabTextColor, unselectedTabTextColor))
                    // Tab 텍스트 Padding 설정
                    it.setPadding(
                        CommonUtil.dpToPx(context, 0f),
                        CommonUtil.dpToPx(context, 0f),
                        CommonUtil.dpToPx(context, 0f),
                        CommonUtil.dpToPx(context, 0f)
                    )
                }

                tabTextList.add(tvTabTitle)
//                customView!!.background = CommonUtil.getDrawableSelectedStateList(context, selectedTabDrawable, unselectedTabDrawable)
//                background = CommonUtil.getDrawableSelectedStateList(context, selectedTabDrawable, unselectedTabDrawable)
//                background = ContextCompat.getDrawable(context, R.drawable.tab_indicator_background)
//                customView!!.background = ContextCompat.getDrawable(context, R.drawable.tab_selection_background)
            }
            eachTabList.add(tab)
            binding.tabLayout.addTab(tab)
        }


        // TabLayout의 Tab 간의 간격 수정
//        wrapTabIndicatorToTitle(binding.tabLayout, 0, 0)

        // TabLayout Background 색 설정
//        binding.tabLayout.setBackgroundResource(tabLayoutBackground)
//        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(context, tabLayoutBackgroundColor))

        binding.tabLayout.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // 사용 시 뷰의 width가 wrap_content인지 아닌지에 따라 설정이 다르다.
                if(isWrapContent) {
                    for(i in 0 until tabTextList.size) {
                        val tabTextPx = tabTextList[i].width ?: 0 // 탭 너비 (px)
//                    val layoutParams = tabTextList[i].layoutParams
//                    layoutParams?.width = tabWidthPx
//                    tabTextList[i].layoutParams = layoutParams
                        wrapTabIndicatorToTitle(binding.tabLayout, 0, 0, 0)
                        Log.d("MYTAG", "text width: ${tabTextPx}")
                        val params = binding.tabLayout.getTabAt(i)?.view?.layoutParams
                        params?.width = tabTextPx * 2
                        binding.tabLayout.getTabAt(i)?.view?.layoutParams = params
                    }
                    Log.d("MYTAG", "---------")
                } else {

                }

//                for(i in 0 until tabTextList.size) {
//                    val tabWidthPx = binding.tabLayout.getTabAt(i)?.view?.width ?: 0 // 탭 너비 (px)
//                    wrapTabIndicatorToTitle(binding.tabLayout, 0, 0, tabWidthPx/tabList.size)
//                    Log.d("MYTAG","탭 너비: ${tabWidthPx}")
//                }
                binding.tabLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun wrapTabIndicatorToTitle(
        tabLayout: TabLayout,
        externalMargin: Int,
        internalMargin: Int,
        minimumWidth: Int
    ) {
        val tabStrip = tabLayout.getChildAt(0)
        if (tabStrip is ViewGroup) {
            val childCount = tabStrip.childCount
            for (i in 0 until childCount) {
                val tabView = tabStrip.getChildAt(i)
                tabView.minimumWidth = CommonUtil.dpToPx(context, minimumWidth.toFloat())
                tabView.setPadding(
                    CommonUtil.dpToPx(context, 5f),
                    tabView.paddingTop,
                    CommonUtil.dpToPx(context, 5f),
                    tabView.paddingBottom
                )
                if (tabView.layoutParams is MarginLayoutParams) {
                    val layoutParams = tabView.layoutParams as MarginLayoutParams
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin)
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin)
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin)
                    }
                }
            }
            tabLayout.requestLayout()
        }
    }

    private fun settingMargin(layoutParams: MarginLayoutParams, start: Int, end: Int) {
        layoutParams.marginStart = start
        layoutParams.marginEnd = end
        layoutParams.leftMargin = start
        layoutParams.rightMargin = end
    }

    private fun createTabView(): View {
        val tabView = LayoutInflater.from(context).inflate(R.layout.item_tab_selection, null)

        return tabView
    }

    private fun tabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentTab = tab?.tag as Int
                tabChangeListener?.onTabSelected(tabList[currentTab])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                currentTab = tab?.tag as Int
                tabChangeListener?.onTabReselected(tabList[currentTab])
            }
        })
    }

    interface TabChangeListener {
        fun onTabSelected(tabTitle: String)
        fun onTabReselected(tabTitle: String)
    }

    private var tabChangeListener: TabChangeListener? = null
    fun onTabChangeListener(tabChangeListener: TabChangeListener) {
        this.tabChangeListener = tabChangeListener
    }

}