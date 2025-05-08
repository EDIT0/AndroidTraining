package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.ActiveButton
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentDateTimePickerViewBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DateTimePickerViewFragment : BaseDataBindingFragment<FragmentDateTimePickerViewBinding>(R.layout.fragment_date_time_picker_view) {

    private var selectedDateTime = MutableStateFlow<String>("")

    private val _isActiveButton = MutableStateFlow<Boolean>(false)
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

        binding.vDateTimePicker.apply {
            setPickerDividerHeight(0f)
            setDateFromTo(from = 0, to = 6)
            changedDateTimeListener = {
                selectedDateTime.value = it
                showToast(selectedDateTime.value)
                _isActiveButton.value = true
            }
        }

        binding.cvActiveButton.setContent {
            val isEnabled = isActiveButton.collectAsState()

            ActiveButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onButtonClick = {
                    showToast(selectedDateTime.value)
                    findNavController().popBackStack()
                },
                isEnable = isEnabled.value,
                title = "선택하기",
                titleColor = Pair(com.hs.workation.core.resource.R.color.white, com.hs.workation.core.resource.R.color.white),
                titleSize = 16f,
                cornerRadius = 10f,
                backgroundColor = Pair(com.hs.workation.core.resource.R.color.grey_500, com.hs.workation.core.resource.R.color.light_blue_300),
                borderStrokeColor = Pair(com.hs.workation.core.resource.R.color.transparent, com.hs.workation.core.resource.R.color.transparent)
            )
        }
    }

    override fun onDestroyView() {
        binding.vDateTimePicker.jobCancel() // job cancel
        super.onDestroyView()
    }
}