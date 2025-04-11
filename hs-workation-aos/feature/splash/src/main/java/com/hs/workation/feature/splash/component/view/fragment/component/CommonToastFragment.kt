package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonToast
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentCommonToastBinding

class CommonToastFragment : BaseDataBindingFragment<FragmentCommonToastBinding>(R.layout.fragment_common_toast) {

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

        binding.btnToast.setOnClickListener {
            CommonToast.makeToast(view = binding.root, "토스트 메시지 !!")
        }
    }
}