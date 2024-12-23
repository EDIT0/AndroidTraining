package com.hs.workation.core.model.mock

data class HorizontalScrollItem(
    val imageUrl: String? = null,
    val title: String? = null,
    override var type: HorizontalItemScrollAdapterViewHolderType
): HorizontalItemScrollAdapterTypeModel
