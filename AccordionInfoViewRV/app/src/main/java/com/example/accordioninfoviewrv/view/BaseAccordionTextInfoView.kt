package com.example.accordioninfoviewrv.view

interface BaseAccordionTextInfoView {

    fun setTitleTextSize(sizeSp: Int)
    fun setContentsTextSize(sizeSp: Int)
    fun setTitleTextColor(color: Int)
    fun setContentsTextColor(color: Int)
    fun setContentsTextBackgroundColor(color: Int)
    fun setIconSize(widthDp: Int?, heightDp: Int?)
    fun setIcon(imgDrawable: Int)
    fun setShowLine(isShow: Boolean)
    fun setLineColor(color: Int)
    fun setLineHeight(heightDp: Float)

    interface AccordionTextInfoContracts: BaseAccordionTextInfoView {
        fun initializeAndSetList(list: List<AccordionTextInfoModel>)
    }

    interface AccordionTextInfoAdapter: BaseAccordionTextInfoView {
        fun getLineHeight(): Float
        fun getLineColor(): Int
        fun getShowLine(): Boolean
    }
}