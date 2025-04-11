package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.ActiveButton
import com.hs.workation.core.component.CommonToast
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentActiveButtonBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActiveButtonFragment : BaseDataBindingFragment<FragmentActiveButtonBinding>(R.layout.fragment_active_button) {

    private val _isActiveButton = MutableStateFlow<Boolean>(true)
    private val isActiveButton: StateFlow<Boolean> = _isActiveButton

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

        binding.btnActiveInactive.setOnClickListener {
            _isActiveButton.value = !_isActiveButton.value
        }
        viewLifecycleOwner.lifecycleScope.launch {
            isActiveButton.collect {
                if(it) {
                    binding.btnActiveInactive.text = "비활성시키기"
                } else {
                    binding.btnActiveInactive.text = "활성시키기"
                }
            }
        }

        binding.cvCompose.setContent {
            val isActive = isActiveButton.collectAsState()

            Column {
                ActiveButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    onButtonClick = {
                        CommonToast.makeToast(binding.root, "버튼 눌림")
                    },
                    isEnable = isActive.value,
                    title = "Button Title",
                    titleColor = Pair(com.hs.workation.core.common.R.color.grey_600, com.hs.workation.core.common.R.color.blue_500),
                    titleSize = 16f,
                    elevation = 5,
                    cornerRadius = 20f,
                    backgroundColor = Pair(com.hs.workation.core.common.R.color.grey_400, com.hs.workation.core.common.R.color.white),
                    borderStroke = 0.5f,
                    borderStrokeColor = Pair(com.hs.workation.core.common.R.color.grey_500, com.hs.workation.core.common.R.color.teal_200)
                )
            }
        }

    }
}