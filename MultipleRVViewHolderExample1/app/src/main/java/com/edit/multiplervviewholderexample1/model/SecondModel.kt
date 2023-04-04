package com.edit.multiplervviewholderexample1.model

import com.edit.multiplervviewholderexample1.ViewHolderType

data class SecondModel(
    override var idx: Int,
    var secondData: String,
    override var type: ViewHolderType
) : Model {
}