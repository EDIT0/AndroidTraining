package com.example.accordioninfoviewrv.view

interface BaseAccordionTextInfoView {

    fun setTitleTextSize(sizeSp: Int)
    fun setContentsTextSize(sizeSp: Int)
    fun setTitleTextColor(color: Int)
    fun setContentsTextColor(color: Int)
    fun setIconSize(widthDp: Int?, heightDp: Int?)
    fun setIcon(imgDrawable: Int)

    interface AccordionTextInfoContracts: BaseAccordionTextInfoView {
        fun initializeAndSetList(list: List<AccordionTextInfoModel>)
    }


}