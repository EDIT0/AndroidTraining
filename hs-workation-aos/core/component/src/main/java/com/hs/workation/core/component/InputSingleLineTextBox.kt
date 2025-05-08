package com.hs.workation.core.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hs.workation.core.resource.R

/**
 * 싱글 라인 텍스트 입력기
 *
 * @param changedText
 * @param onDoneClick
 * @param isInitKeyboardUp 시작 시 키보드 올릴지 여부
 * @param modifier
 * @param isEnable
 * @param labelText
 * @param textSize
 * @param hintText
 * @param leadingIcon
 * @param isShowClearTextIcon 텍스트 삭제 아이콘 여부
 * @param prefix
 * @param suffix
 * @param supportingTextComposable Text()
 * @param visualTransformationType 텍스트 입력 폼 결정
 * @param isPassword
 * @param keyboardInputType 키보드 형태 타입 결정
 * @param cornerRadius
 * @param backgroundColor first: unfocused, second: focused
 * @param underlineColor first: unfocused, second: focused
 */
@Composable
fun InputSingleLineTextBox(
    changedText: (String) -> Unit,
    onDoneClick: () -> Unit,
    isInitKeyboardUp: Boolean,
    modifier: Modifier = Modifier,
    isEnable: Boolean,
    labelText: String?,
    textSize: Float,
    hintText: String?,
    leadingIcon: Int?,
    isShowClearTextIcon: Boolean,
    prefix: String?,
    suffix: String?,
    supportingTextComposable: @Composable (() -> Unit)?,
    visualTransformationType: VisualTransformationType = VisualTransformationType.NORMAL,
    isPassword: Boolean,
    keyboardInputType: KeyboardInputType,
    cornerRadius: Int,
    backgroundColor: Pair<Int, Int>, // unfocused, focused
    underlineColor: Pair<Int, Int> // unfocused, focused
) {

    val focusManager = LocalFocusManager.current

    val text = remember {
        mutableStateOf("")
    }

    val pwVisibility = remember {
        mutableStateOf(false)
    }

    val initLoad = remember {
        mutableStateOf(true)
    }
    val focusRequester = remember {
        FocusRequester()
    }

    Row(
        modifier = modifier
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text.value,
            onValueChange = {
                when(visualTransformationType) {
                    VisualTransformationType.NORMAL -> {
                        text.value = it
                        changedText.invoke(text.value)
                    }
                    VisualTransformationType.IDENTITY -> {
                        if(it.length <= IDENTITY_LENGTH) {
                            text.value = it
                            changedText.invoke(text.value)
                        }
                    }
                    VisualTransformationType.PHONE_NUMBER -> {
                        if(it.length <= PHONE_NUMBER_LENGTH) {
                            text.value = it
                            changedText.invoke(text.value)
                        }
                    }
                    VisualTransformationType.CARD -> {
                        if(it.length <= CARD_LENGTH) {
                            text.value = it
                            changedText.invoke(text.value)
                        }
                    }
                    VisualTransformationType.CAR_LICENCE -> {
                        if(it.length <= CAR_LICENCE_LENGTH) {
                            text.value = it
                            changedText.invoke(text.value)
                        }
                    }
                }
            },
            modifier = modifier
                .then(modifier)
                .wrapContentHeight()
                .focusRequester(focusRequester),
            enabled = isEnable,
            readOnly = false,
            textStyle = TextStyle(fontSize = textSize.sp),
            label = labelText?.let { text ->
                {
                    Text(text = text)
                }
            },
            placeholder = hintText?.let { text ->
                {
                    Text(text = text)
                }
            },
            leadingIcon = leadingIcon?.let { icon ->
                {
                    Icon(painter = painterResource(id = icon), contentDescription = "")
                }
            },
            trailingIcon = if(isShowClearTextIcon && text.value.isNotEmpty()) {
                {
                    if(isPassword) {
                        Row {
                            IconButton(
                                modifier = Modifier
                                    .size(36.dp),
                                onClick = {
                                    pwVisibility.value = !pwVisibility.value
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = if (pwVisibility.value) {
                                        R.drawable.ic_visibility_on
                                    } else {
                                        R.drawable.ic_visibility_off
                                    }
                                    ),
                                    contentDescription = ""
                                )
                            }
                            IconButton(
                                modifier = Modifier
                                    .size(36.dp),
                                onClick = {
                                    text.value = ""
                                    changedText.invoke(text.value)
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    } else {
                        IconButton(
                            onClick = {
                                text.value = ""
                                changedText.invoke(text.value)
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                        }
                    }
                }
            } else {
                null
            },
            prefix = prefix?.let {
                {
                    Text(text = prefix)
                }
            },
            suffix = suffix?.let {
                {
                    Text(text = suffix)
                }
            },
            supportingText = supportingTextComposable?.let{
                {
                    supportingTextComposable.invoke()
                }
            },
            isError = false,
//            visualTransformation = IdentityTransformation(),
            visualTransformation = if(isPassword) {
                if(pwVisibility.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            } else {
                when(visualTransformationType) {
                    VisualTransformationType.NORMAL -> {
                        VisualTransformation.None
                    }
                    VisualTransformationType.IDENTITY -> {
                        IdentityTransformation()
                    }
                    VisualTransformationType.PHONE_NUMBER -> {
                        PhoneNumberTransformation()
                    }
                    VisualTransformationType.CARD -> {
                        CardTransformation()
                    }
                    VisualTransformationType.CAR_LICENCE -> {
                        CarLicenceTransformation()
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = when(keyboardInputType) {
                    KeyboardInputType.NORMAL -> {
                        KeyboardType.Text
                    }
                    KeyboardInputType.NUMBER -> {
                        KeyboardType.NumberPassword
                    }
                    KeyboardInputType.PASSWORD -> {
                        KeyboardType.Password
                    }
                }
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    onDoneClick.invoke()
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            maxLines = 0,
            minLines = 1,
//            interactionSource = {
//
//            },
            shape = RoundedCornerShape(cornerRadius.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(id = backgroundColor.first),
                focusedContainerColor = colorResource(id = backgroundColor.second),
                unfocusedIndicatorColor = colorResource(id = underlineColor.first),
                focusedIndicatorColor = colorResource(id = underlineColor.second)
            )
        )
    }

    LaunchedEffect(initLoad.value) {
        if(initLoad.value) {
            initLoad.value = false
            if(isInitKeyboardUp) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InputSingleLineTextBoxPreview() {
    InputSingleLineTextBox(
        changedText = {

        },
        onDoneClick = {

        },
        isInitKeyboardUp = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        isEnable = true,
        labelText = null,
        textSize = 20f,
        hintText = "HintText",
        leadingIcon = null,
        isShowClearTextIcon = true,
        prefix = null,
        suffix = null,
        supportingTextComposable = null,
        visualTransformationType = VisualTransformationType.NORMAL,
        isPassword = true,
        keyboardInputType = KeyboardInputType.PASSWORD,
        cornerRadius = 20,
        backgroundColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.grey_600, com.hs.workation.core.resource.R.color.teal_200),
        underlineColor = Pair<Int, Int>(com.hs.workation.core.resource.R.color.grey_600, com.hs.workation.core.resource.R.color.teal_200)
    )
}