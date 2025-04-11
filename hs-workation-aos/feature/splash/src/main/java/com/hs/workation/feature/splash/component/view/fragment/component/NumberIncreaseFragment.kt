package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.NumberIncrease
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentNumberIncreaseBinding

class NumberIncreaseFragment : BaseDataBindingFragment<FragmentNumberIncreaseBinding>(R.layout.fragment_number_increase) {


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

        binding.cvNumberIncrease.setContent {
            // mock data
            val people = remember { mutableStateOf(5) }
            val kids = remember { mutableStateOf(0) }

            Column {
                NumberIncrease(people, minimum = 5, maximum = 10)
                NumberIncrease(kids, maximum = 5)
            }
        }
    }
}