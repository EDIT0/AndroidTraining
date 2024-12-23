package com.hs.workation.feature.splash.view.fragment.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.TimelineView
import com.hs.workation.core.model.mock.Timeline
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentTimelineBinding

class TimelineFragment : BaseDataBindingFragment<FragmentTimelineBinding>(R.layout.fragment_timeline) {

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

        // mockup data
        val items = listOf("1", "2", "3", "4", "5")
        val list = listOf(
            Timeline("aaaa", items),
            Timeline("bbbb", items),
            Timeline("cccc", items)
        )

        binding.cvTimeline.setContent {
            TimelineView(list)
        }
    }
}