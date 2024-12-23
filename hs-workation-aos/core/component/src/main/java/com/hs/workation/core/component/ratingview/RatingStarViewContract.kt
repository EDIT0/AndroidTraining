package com.hs.workation.core.component.ratingview

interface RatingStarViewContract {
    fun setTotalStarCount(count: Int)
    fun setCurrentStar(rating: Float)
    fun setOneStepSize(size: Float)
}