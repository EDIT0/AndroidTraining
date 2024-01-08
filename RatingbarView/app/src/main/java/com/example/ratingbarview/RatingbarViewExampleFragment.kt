package com.example.ratingbarview

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ratingbarview.databinding.FragmentRatingbarViewExampleBinding
import kotlin.random.Random

class RatingbarViewExampleFragment : Fragment() {

    private lateinit var binding: FragmentRatingbarViewExampleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRatingbarViewExampleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratingBar.apply {
            setStarCount(5)
            setStepSize(0.5f)
            setIsIndicator(false)
            isClickable = true
            setEmptyImage(R.drawable.kid_star_empty_48)
            setFillImage(R.drawable.kid_star_fill_48)
            setStarPadding(0)
            setCurrentStar(3f)
            width = dpToPx(binding.root.context, 30f) // 별 하나 당 30dp
            height = dpToPx(binding.root.context, 30f) // 별 하나 당 30dp
            onRatingChangeListener(object : RatingView.RatingChangeListener {
                override fun change(rating: Float, fromUser: Boolean) {
                    binding.tvStarCount.text = "별 갯수: ${binding.ratingBar.getCurrentStar()} / 유저 클릭: ${fromUser}"
                }
            })
        }

        binding.btnUpdate.setOnClickListener {
            binding.ratingBar.setCurrentStar(Random.nextInt(binding.ratingBar.getMinStarsCount().toInt(), binding.ratingBar.getMaxStars() + 1).toFloat())
        }

    }

    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}