package com.hs.workation.core.component.passcodeview

interface PasscodeViewContract {
    fun setInfoText(text: String)
    fun numberCount(count: Int)
    fun onPressedNumberListener(number: String)
    fun onRemoveNumberListener()
}