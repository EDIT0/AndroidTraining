package com.hs.workation.feature.splash.component.view.fragment.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.ImageCarousel
import com.hs.workation.core.component.ImageCarouselNavigator
import com.hs.workation.core.model.mock.SpaceInfo
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentImageCarouselBinding

class ImageCarouselFragment : BaseDataBindingFragment<FragmentImageCarouselBinding>(R.layout.fragment_image_carousel) {
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

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvImageCarousel.setContent {
            // mockup data
            val data = SpaceInfo(
                name = "다목적홀",
                description = "수용가능 30인",
                pricePerUnit = 10000,
                unit = "30분",
                thumbnails = listOf(
                    "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/30d26bb8-153b-473b-b82b-1c8c86062e94.jpeg",
                    "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/939f3659-d4a5-4238-ab79-f499d18e81e1.jpeg",
                    "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/5dfa278f-44ff-4a16-8d08-e4971ecad3cb.jpeg"
                )
            )

            ImageCarousel(data = data)
        }

        binding.cvImageCarouselNavigator.setContent {
            // mockup data
            val data = listOf(
                "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/30d26bb8-153b-473b-b82b-1c8c86062e94.jpeg",
                "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/939f3659-d4a5-4238-ab79-f499d18e81e1.jpeg",
                "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/5dfa278f-44ff-4a16-8d08-e4971ecad3cb.jpeg"
            )

            ImageCarouselNavigator(data = data)
        }
    }

}