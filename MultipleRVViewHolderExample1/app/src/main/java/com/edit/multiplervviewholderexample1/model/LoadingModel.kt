package com.edit.multiplervviewholderexample1.model

import com.edit.multiplervviewholderexample1.ViewHolderType

data class LoadingModel(
    override var idx: Int,
    override var type: ViewHolderType
): Model {
}