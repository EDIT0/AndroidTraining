package com.edit.multiplervviewholderexample1.model

import com.edit.multiplervviewholderexample1.ViewHolderType

data class ThirdModel(
    override var idx: Int,
    var thirdData: String,
    override var type: ViewHolderType
): Model {
}