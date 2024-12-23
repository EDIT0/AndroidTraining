package com.hs.workation.core.component.horizontalscrollview

import com.hs.workation.core.model.mock.HorizontalItemScrollAdapterTypeModel

interface HorizontalItemScrollViewContract {
    fun setViewPadding(startPadding: Int, endPadding: Int, topPadding: Int, bottomPadding: Int)
    fun setHorizontalClipToPadding(isClip: Boolean)
    fun setImageSize(width: Int, height: Int)
    fun setItemToItemMargin(margin: Int)
    fun setItem(list: List<HorizontalItemScrollAdapterTypeModel>)
}