package com.hs.workation.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 다중 선택 버튼 그룹
 *
 * @param list : mock data
 * @param buttonHeight : 버튼 높이
 * @param spacerSize : 아이템 간격
 * @param onClickButton : 클릭 이벤트
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GroupButtonFlow(
    list: List<String>,
    buttonHeight: Int = 33,
    spacerSize: Int = 6,
    onClickButton: (String) -> Unit
) {
    val selectedItem = remember { mutableStateListOf<String>() }

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        list.mapIndexed { index, item ->
            val isSelected = remember { mutableStateOf(false) }

            OutlinedButton(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .defaultMinSize(minHeight = buttonHeight.dp)
                    .heightIn(max = buttonHeight.dp),
                onClick = {
                    if (isSelected.value) {
                        // 한번 더 클릭 시 취소
                        isSelected.value = false
                        selectedItem.remove(item)
                        onClickButton("$item 선택 취소")
                    } else {
                        isSelected.value = true
                        selectedItem.add(item)
                        onClickButton("${selectedItem.joinToString(",")} 선택")
                    }
                },
                shape = ShapeDefaults.Medium,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = getColors(isSelected.value).first,
                    contentColor = getColors(isSelected.value).second
                ),
                border = BorderStroke(1.dp, getColors(isSelected.value).second),
                contentPadding = PaddingValues(18.dp, 0.dp)
            ) {
                Text(text = item, fontSize = 14.sp)
            }

            if (index != list.lastIndex) {
                Spacer(Modifier.size(spacerSize.dp))
            }
        }
    }
}

/**
 * 선택 여부에 따른 버튼 컬러
 *
 * @param isActivate
 * @return
 */
@Composable
private fun getColors(isActivate: Boolean): Pair<Color, Color> {
    val activeContainerColor = colorResource(com.hs.workation.core.common.R.color.grey_900)
    val activeContentColor = Color.White
    val containerColor = Color.Transparent
    val contentColor = colorResource(com.hs.workation.core.common.R.color.grey_400)

    return if (isActivate) {
        Pair(activeContainerColor, activeContentColor)
    }
    else {
        Pair(containerColor, contentColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun IconButtonGroupFlowPreview() {
    // mockup data
    val list = listOf("조용함", "안전운전", "청결/쾌적", "친절함", "기분 좋은 인사", "빠른 도착", "감동 서비스")

    GroupButtonFlow(list) { }
}