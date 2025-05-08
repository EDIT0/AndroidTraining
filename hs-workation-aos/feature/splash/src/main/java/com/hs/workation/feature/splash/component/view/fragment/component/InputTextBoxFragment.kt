package com.hs.workation.feature.splash.component.view.fragment.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.InputMultiLineTextBox
import com.hs.workation.core.component.InputMultiOutlineTextBox
import com.hs.workation.core.component.InputSingleLineTextBox
import com.hs.workation.core.component.InputSingleOutlineTextBox
import com.hs.workation.core.component.KeyboardInputType
import com.hs.workation.core.component.VisualTransformationType
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentInputTextBoxBinding
import kotlinx.coroutines.flow.MutableStateFlow

class InputTextBoxFragment : BaseDataBindingFragment<FragmentInputTextBoxBinding>(R.layout.fragment_input_text_box) {

    private var _text = MutableStateFlow("")
    private val text get() = _text

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

        binding.cvCompose.setContent {
            val currentText by text.collectAsState()
            LogUtil.d_dev("currentText: ${currentText} / text: ${text.value}")

            Column(
                modifier = Modifier
                    .imePadding(), // 키보드가 입력창 가릴 경우, 보일 수 있도록
            ) {
                InputSingleOutlineTextBox(
                    changedText = {
                        _text.value = it
                    },
                    onDoneClick = {
                        LogUtil.d_dev("완료 클릭")
                    },
                    isInitKeyboardUp = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp, horizontal = 10.dp),
                    isEnable = true,
                    labelText = null,
                    textSize = 20f,
                    hintText = "HintText",
                    leadingIcon = null,
                    isShowClearTextIcon = true,
                    prefix = null,
                    suffix = null,
                    supportingTextComposable = {
                        if(currentText.isNotEmpty()) {
                            Text(text = "O", color = colorResource(id = com.hs.workation.core.resource.R.color.blue_500))
                        } else {
                            Text(text = "X", color = colorResource(id = com.hs.workation.core.resource.R.color.red_700))
                        }
                    },
                    visualTransformationType = VisualTransformationType.NORMAL,
                    isPassword = true,
                    keyboardInputType = KeyboardInputType.PASSWORD,
                    cornerRadius = 20,
                    backgroundColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.grey_600, com.hs.workation.core.resource.R.color.blue_500),
                    borderColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.grey_600, com.hs.workation.core.resource.R.color.teal_200)
                )

                InputSingleLineTextBox(
                    changedText = {
                        _text.value = it
                    },
                    onDoneClick = {
                        LogUtil.d_dev("완료 클릭")
                    },
                    isInitKeyboardUp = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp, horizontal = 10.dp),
                    isEnable = true,
                    labelText = null,
                    textSize = 20f,
                    hintText = "HintText",
                    leadingIcon = null,
                    isShowClearTextIcon = true,
                    prefix = null,
                    suffix = null,
                    supportingTextComposable = {
                        if(currentText.isNotEmpty()) {
                            Text(text = "O", color = colorResource(id = com.hs.workation.core.resource.R.color.blue_500))
                        } else {
                            Text(text = "X", color = colorResource(id = com.hs.workation.core.resource.R.color.red_700))
                        }
                    },
                    visualTransformationType = VisualTransformationType.CAR_LICENCE,
                    isPassword = false,
                    keyboardInputType = KeyboardInputType.PASSWORD,
                    cornerRadius = 0,
                    backgroundColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.transparent, com.hs.workation.core.resource.R.color.transparent),
                    underlineColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.transparent, com.hs.workation.core.resource.R.color.teal_200)
                )

                InputMultiLineTextBox(
                    changedText = {
                        _text.value = it
                    },
                    isInitKeyboardUp = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp, horizontal = 10.dp),
                    isEnable = true,
                    labelText = null,
                    textSize = 16f,
                    hintText = "HintText",
                    leadingIcon = null,
                    isShowClearTextIcon = false,
                    prefix = null,
                    suffix = null,
                    supportingTextComposable = null,
                    visualTransformationType = VisualTransformationType.PHONE_NUMBER,
                    keyboardInputType = KeyboardInputType.NORMAL,
                    maxLines = 5,
                    minLines = 3,
                    cornerRadius = 5,
                    backgroundColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.blue_500, com.hs.workation.core.resource.R.color.yellow_200),
                    underlineColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.transparent, com.hs.workation.core.resource.R.color.transparent)
                )

                InputMultiOutlineTextBox(
                    changedText = {
                        _text.value = it
                    },
                    isInitKeyboardUp = false,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 10.dp),
                    isEnable = true,
                    labelText = null,
                    textSize = 16f,
                    hintText = "HintText",
                    leadingIcon = null,
                    isShowClearTextIcon = false,
                    prefix = null,
                    suffix = null,
                    supportingTextComposable = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = "${currentText.length}/1000")
                        }
                    },
                    visualTransformationType = VisualTransformationType.NORMAL,
                    keyboardInputType = KeyboardInputType.NORMAL,
                    maxLines = 6,
                    minLines = 3,
                    cornerRadius = 0,
                    backgroundColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.transparent, com.hs.workation.core.resource.R.color.transparent),
                    borderColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.yellow_200, com.hs.workation.core.resource.R.color.blue_500)
                )
            }
        }
    }
}