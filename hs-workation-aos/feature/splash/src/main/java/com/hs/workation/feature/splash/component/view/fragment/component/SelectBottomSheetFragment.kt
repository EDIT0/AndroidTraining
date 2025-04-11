package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonSelectSheet
import com.hs.workation.core.component.CommonToast
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentSelectBottomBinding

class SelectBottomSheetFragment : BaseDataBindingFragment<FragmentSelectBottomBinding>(R.layout.fragment_select_bottom) {

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

        binding.btnSheet.setOnClickListener {
            // mock data
            val list = listOf("미팅룸", "다목적 홀 대관", "오픈 데스크","미팅룸", "다목적 홀 대관", "오픈 데스크","미팅룸", "다목적 홀 대관", "오픈 데스크","미팅룸", "다목적 홀 대관", "오픈 데스크","미팅룸", "다목적 홀 대관", "오픈 데스크","미팅룸", "다목적 홀 대관", "오픈 데스크")

            CommonSelectSheet(
                title = "타입을 선택해 주세요",
                list = list,
                resultCallback = { item ->
                    CommonToast.makeToast(view, item)
                }
            ).show(requireActivity().supportFragmentManager, "")
        }
    }
}