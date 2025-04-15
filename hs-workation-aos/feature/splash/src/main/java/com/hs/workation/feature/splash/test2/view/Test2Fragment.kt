package com.hs.workation.feature.splash.test2.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentTest2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Test2Fragment : BaseDataBindingFragment<FragmentTest2Binding>(R.layout.fragment_test_2){

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

        setStatusBarPadding(view = binding.root, isPadding = true, isStatusIconBlack = true)
    }

    override fun onDetach() {
        backPressCallback?.remove()
        super.onDetach()
    }
}