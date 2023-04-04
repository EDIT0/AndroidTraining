package com.edit.multiplervviewholderexample1.model

import com.edit.multiplervviewholderexample1.ViewHolderType

data class FirstModel(
    override var idx: Int,
    var firstData: String,
    override var type: ViewHolderType
): Model
