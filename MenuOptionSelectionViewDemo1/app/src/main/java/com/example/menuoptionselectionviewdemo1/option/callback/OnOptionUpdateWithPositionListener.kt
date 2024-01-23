package com.example.menuoptionselectionviewdemo1.option.callback

import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel

interface OnOptionUpdateWithPositionListener {

    fun onOptionUpdateWithPosition(optionModel: MenuOptionModel.OptionModel, position: Int)

}