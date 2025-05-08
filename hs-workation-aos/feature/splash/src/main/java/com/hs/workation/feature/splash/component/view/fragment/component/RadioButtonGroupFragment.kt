package com.hs.workation.feature.splash.component.view.fragment.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.res.painterResource
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonToast
import com.hs.workation.core.component.GroupIconButton
import com.hs.workation.core.component.GroupButtonFlow
import com.hs.workation.core.component.GroupRadioButton
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentRadioButtonGroupBinding

class RadioButtonGroupFragment : BaseDataBindingFragment<FragmentRadioButtonGroupBinding>(R.layout.fragment_radio_button_group) {

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

        binding.cvRadioButtonGroup.setContent {
            // mockup data
            val list = listOf("+ 10분", "+ 20분", "+ 30분", "+ 1시간", "+ 1시간 30분")

            GroupRadioButton(
                list = list,
                onClickButton = { index, item ->
                    if (index == null) {
                        CommonToast.makeToast(view, item)
                    } else {
                        CommonToast.makeToast(view, "$index, $item")
                    }
                }
            )
        }

        binding.cvIconButtonGroup.setContent {
            // mockup data
            val icon = painterResource(com.hs.workation.core.resource.R.drawable.ic_visibility_on)
            val list = listOf(
                Pair("오픈데스크", icon),
                Pair("미팅룸 A", icon),
                Pair("미팅룸 B", icon),
                Pair("미팅룸 C", icon),
                Pair("다목적홀", icon)
            )

            GroupIconButton(list) { item ->
                CommonToast.makeToast(view, item)
            }
        }

        binding.cvButtonGroupFlow.setContent {
            // mockup data
            val list = listOf("조용함", "안전운전", "청결/쾌적", "친절함", "기분 좋은 인사", "빠른 도착", "감동 서비스")

            GroupButtonFlow(list) { item ->
                CommonToast.makeToast(view, item)
            }
        }
    }
}