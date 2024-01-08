package com.example.ratingbarview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.ratingbarview.databinding.RatingViewBinding


class RatingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: RatingViewBinding = RatingViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var fillImgDefault = R.drawable.star_filled
    private var fillImg: Int? = null
//    private var halfImgDefault = R.drawable.star_half
//    private var halfImg: Int? = null
    private var emptyImgDefault = R.drawable.star_empty
    private var emptyImg: Int? = null

    private var minimumStars = 0f

    init {
        // Default Setting
        binding.ratingBarView.apply {
            numStars = 5
            stepSize = 1f
            setIsIndicator(false)
//            setEmptyDrawableRes(emptyImgDefault)
//            setFilledDrawableRes(fillImgDefault)
            isScrollable = true
            isClickable = true
        }
    }

    fun setStarCount(count: Int) {
        binding.ratingBarView.numStars = count
    }

    fun setCurrentStar(count: Float) {
        binding.ratingBarView.rating = count
    }

    fun setStepSize(size: Float) {
        binding.ratingBarView.stepSize = size
    }

    fun setIsIndicator(indicator: Boolean) {
        binding.ratingBarView.setIsIndicator(indicator)
    }

    fun setEmptyImage(img: Int) {
        emptyImg = img
        binding.ratingBarView.setEmptyDrawableRes(emptyImg?:emptyImgDefault)
    }

    fun setFillImage(img: Int) {
        fillImg = img
        binding.ratingBarView.setFilledDrawableRes(fillImg?:fillImgDefault)
    }

    fun setMinWidth(width: Int) {
        binding.ratingBarView.minimumWidth = width
    }

    fun setWidth(width: Int) {
        binding.ratingBarView.starWidth = width
    }

    fun setMinHeight(height: Int) {
        binding.ratingBarView.minimumHeight = height
    }

    fun setHeight(height: Int) {
        binding.ratingBarView.starHeight = height
    }

    fun setStarPadding(padding: Int) {
        binding.ratingBarView.starPadding = padding
    }

    fun setMinimumStars(minimum: Float) {
        binding.ratingBarView.setMinimumStars(minimum)
    }

    fun setStarsScrollable(scrollable: Boolean) {
        binding.ratingBarView.isScrollable = scrollable
    }

    fun setStarsClickable(clickable: Boolean) {
        binding.ratingBarView.isClickable = clickable
    }

    fun setMinStars(min: Float) {
        minimumStars = min
        binding.ratingBarView.setMinimumStars(min)
    }

    fun getMaxStars(): Int {
        return binding.ratingBarView.numStars
    }

    fun getCurrentStar(): Float {
        return binding.ratingBarView.rating
    }

    fun getMinStarsCount(): Float {
        return minimumStars
    }

    fun onRatingChangeListener(ratingChangeListener: RatingChangeListener) {
        binding.ratingBarView.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            ratingChangeListener.change(rating, fromUser)
        }
    }

    interface RatingChangeListener {
        fun change(rating: Float, fromUser: Boolean)
    }
}