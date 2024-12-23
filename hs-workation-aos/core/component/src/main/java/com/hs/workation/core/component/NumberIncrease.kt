package com.hs.workation.core.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 숫자 증감 컴포넌트
 *
 * @param number
 * @param minimum
 * @param maximum
 */
@Composable
fun NumberIncrease(
    number: MutableState<Int>,
    minimum: Int = 0,
    maximum: Int
) {
    // 최솟값이 default 값인 0이 아닐 경우 초기값 변경
    LaunchedEffect(minimum) {
        if (minimum != 0) {
            number.value = minimum
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        // - 버튼
        IconButton(
            onClick = {
                if (number.value > minimum) number.value--
            },
            enabled = number.value > minimum
        ) {
            Icon(painterResource(R.drawable.ic_minus), contentDescription = "minus")
        }

        Text(
            text = "${number.value}",
            modifier = Modifier.widthIn(min = 15.dp),
            textAlign = TextAlign.Center
        )

        // + 버튼
        IconButton(
            onClick = {
                if (number.value < maximum) number.value++
            },
            enabled = number.value < maximum
        ) {
            Icon(painterResource(R.drawable.ic_plus), contentDescription = "plus")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NumberIncreasePreview() {
    // mock data
    val number = remember { mutableStateOf(0) }
    val number2 = remember { mutableStateOf(10) }

    Column {
        NumberIncrease(number, maximum = 10)
        NumberIncrease(number2, maximum = 10)
    }
}