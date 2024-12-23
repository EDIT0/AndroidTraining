package com.hs.workation.feature.splash.view.fragment.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.ExpandableText
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentExpandableTextBinding

class ExpandableTextFragment : BaseDataBindingFragment<FragmentExpandableTextBinding>(R.layout.fragment_expandable_text) {

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

        binding.cvExpandableText.setContent {
            Column {
                // 단문
                ExpandableText(
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    maxLines = 3)

                Spacer(Modifier.size(40.dp))

                // 장문
                ExpandableText(
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s," +
                        " when an unknown printer took a galley of type and scrambled it to make a type specimen book." +
                        " It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged." +
                        " It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages," +
                        " and more recently with desktop publishing software like Aldus",
                    maxLines = 3)
            }
        }
    }
}