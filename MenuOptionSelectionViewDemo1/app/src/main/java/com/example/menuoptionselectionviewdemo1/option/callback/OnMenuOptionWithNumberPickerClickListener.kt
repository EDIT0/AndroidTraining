package com.example.menuoptionselectionviewdemo1.option.callback

import android.widget.RadioButton
import android.widget.TextView
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel

interface OnMenuOptionWithNumberPickerClickListener {

    fun onMenuOptionWithNumberPickerClick(textView: TextView, isPlus: Boolean, position: Int, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel)
//    fun saveRadioButton(radioButton: RadioButton)

}