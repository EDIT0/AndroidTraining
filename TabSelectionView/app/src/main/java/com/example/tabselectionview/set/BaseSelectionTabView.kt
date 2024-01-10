package com.example.tabselectionview.set

interface BaseSelectionTabView {

    fun setTabLayoutHeight(heightDp: Int)
    fun setTabLayoutBackground(tabLayoutBg: Int)
    fun setTabIndicatorShape(indicatorBg: Int)
    fun setTabIndicatorColor(indicatorColor: Int)
    fun setTabTextColor(selectedTabColor: Int, unselectedTabColor: Int)
    fun setTabBackground(tabBg: Int?)
    fun setIsWrapContent(isWrapContent: Boolean)
    fun setList(list: List<String>)
}