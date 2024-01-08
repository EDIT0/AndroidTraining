package com.example.stepview.view

interface BaseStepView {

    fun setList(list: List<StepViewModel>)
    fun setCurrentStep(step: Int)
    fun getCurrentStep(): Int
    fun openTitle()
    fun closeTitle()
    fun openSubTitle()
    fun closeSubTitle()
    fun setCompletedImg(img: Int)
    fun setSelectedImg(img: Int)
    fun setBeforeImg(img: Int)
    fun setCompletedLineColor(color: Int)
    fun setBeforeLineColor(color: Int)
    fun setCompletedContentsTextColor(color: Int)
    fun setSelectedContentsTextColor(color: Int)
    fun setBeforeContentsTextColor(color: Int)
    fun isStepClickable(clickable: Boolean)

    interface StepViewContracts : BaseStepView {
        fun initStepView()
        fun getStepListSize(): Int
        fun show()
    }
}