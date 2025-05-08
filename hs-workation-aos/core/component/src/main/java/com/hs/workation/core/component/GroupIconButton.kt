package com.hs.workation.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hs.workation.core.resource.R

/**
 * 다중 선택 버튼 그룹
 *
 * @param list : mock data
 * @param rowContentPadding : 스크롤 contentPadding
 * @param buttonHeight : 버튼 높이
 * @param spacerSize : 아이템 간격
 * @param onClickButton : 클릭 이벤트
 */
@Composable
fun GroupIconButton(
    list: List<Pair<String, Painter>>,
    rowContentPadding: Int = 14,
    buttonHeight: Int = 30,
    spacerSize: Int = 6,
    onClickButton: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = rowContentPadding.dp)
    ) {
        itemsIndexed(list) { index, item ->
            val isSelected = remember { mutableStateOf(false) }

            OutlinedButton(
                modifier = Modifier
                    .padding(vertical = 1.dp) // 버튼 짤림 현상 방지
                    .defaultMinSize(minHeight = buttonHeight.dp)
                    .heightIn(max = buttonHeight.dp),
                onClick = {
                    if (isSelected.value) {
                        // 한번 더 클릭 시 취소
                        isSelected.value = false
                        onClickButton("${item.first} 선택 취소")
                    } else {
                        isSelected.value = true
                        onClickButton("${item.first} 선택")
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = getColors(isSelected.value).first,
                    contentColor = getColors(isSelected.value).second
                ),
                border = BorderStroke(1.dp, getColors(isSelected.value).second),
                contentPadding = PaddingValues(10.dp, 0.dp)
            ) {
                Icon(painter = item.second,
                    contentDescription = "icon",
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(20.dp)
                )
                Text(text = item.first, fontSize = 14.sp)
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
    val activeContainerColor = Color.Transparent
    val activeContentColor = colorResource(com.hs.workation.core.resource.R.color.grey_600)
    val containerColor = Color.Transparent
    val contentColor = colorResource(com.hs.workation.core.resource.R.color.grey_400)

    return if (isActivate) {
        Pair(activeContainerColor, activeContentColor)
    }
    else {
        Pair(containerColor, contentColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun IconButtonPreview() {
    // mockup data
    val icon = painterResource(R.drawable.ic_visibility_on)
    val list = listOf(
        Pair("오픈데스크", icon),
        Pair("미팅룸 A", icon),
        Pair("미팅룸 B", icon),
        Pair("미팅룸 C", icon),
        Pair("다목적홀", icon)
    )

    GroupIconButton(list) { }
}