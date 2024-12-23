package com.hs.workation.core.component.ratingview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hs.workation.core.component.databinding.RatingStarViewBinding

/**
 * RatingStar View
 *
 * style의 Min, Max Height를 star image와 똑같이 주어야함.
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
class RatingStarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), RatingStarViewContract {

    private var binding: RatingStarViewBinding = RatingStarViewBinding.inflate(LayoutInflater.from(context), this, true)

    var ratingBarChangeCallback: ((Float) -> Unit)? = null

    init {
        binding.rbStar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingBarChangeCallback?.invoke(rating)
        }
    }

    /**
     * 총 별 갯수 설정
     *
     * @param count
     */
    override fun setTotalStarCount(count: Int) {
        binding.rbStar.numStars = count
    }

    /**
     * 현재 별 갯수 설정
     *
     * @param rating
     */
    override fun setCurrentStar(rating: Float) {
        binding.rbStar.rating = rating
    }

    /**
     * 단계마다 변경되는 별 사이즈 설정
     *
     * @param size
     */
    override fun setOneStepSize(size: Float) {
        binding.rbStar.stepSize = size
    }

}