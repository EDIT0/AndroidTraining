package com.example.menuoptionselectionviewdemo1.option.optionmenu.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class ToggleableRadioButton (context: Context, attrs: AttributeSet? = null) : AppCompatRadioButton(context, attrs) {

    override fun toggle() {
//        isChecked = !isChecked
    }
}