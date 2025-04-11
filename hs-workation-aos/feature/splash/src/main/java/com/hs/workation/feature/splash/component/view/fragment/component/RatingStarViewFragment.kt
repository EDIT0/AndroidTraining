package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentRatingStarViewBinding

class RatingStarViewFragment : BaseDataBindingFragment<FragmentRatingStarViewBinding>(R.layout.fragment_rating_star_view) {

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vRatingStar.apply {
            setTotalStarCount(5)
            setOneStepSize(0.5f)
            setCurrentStar(0f)
            ratingBarChangeCallback = { rating ->
                showToast("${rating}점")
            }
        }

    }
}