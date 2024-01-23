package com.example.menuoptionselectionviewdemo1.option.callback

import android.widget.RadioButton
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel

interface OnMenuOptionWithRadioButtonClickListener {
    fun onMenuOptionWithRadioButtonClick(radioButton: RadioButton, position: Int, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel)
    fun saveRadioButton(radioButton: RadioButton)
}