package com.example.menuoptionselectionviewdemo1.option.callback

import android.widget.CheckBox
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel

interface OnMenuOptionWithCheckBoxClickListener {

    fun onMenuOptionWithCheckBoxClick(checkBox: CheckBox, position: Int, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel)
    fun saveCheckBox(checkBox: CheckBox)

}