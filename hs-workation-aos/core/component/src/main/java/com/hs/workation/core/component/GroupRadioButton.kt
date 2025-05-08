package com.hs.workation.core.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 라디오 버튼 그룹
 *
 * @param list : 버튼 목록
 * @param rowContentPadding : 스크롤 contentPadding
 * @param buttonHeight : 버튼 높이
 * @param onClickButton : 데이터 형식에 따라 id, value 값을 인자로 넘김
 */
@Composable
fun GroupRadioButton(
    list: List<String>,
    rowContentPadding: Int = 28,
    buttonHeight: Int = 33,
    onClickButton: (Int?, String) -> Unit
) {
    val selectedIndex = remember { mutableStateOf<Int?>(null) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = rowContentPadding.dp)
    ) {
        itemsIndexed(list) { index, item ->
            Button(
                modifier = Modifier
                    .padding(vertical = 1.dp) // 버튼 짤림 현상 방지
                    .defaultMinSize(minHeight = buttonHeight.dp)
                    .heightIn(max = buttonHeight.dp),
                onClick = {
                    if (selectedIndex.value == index) {
                        // 한번 더 클릭 시 취소
                        selectedIndex.value = null
                        onClickButton(selectedIndex.value, "$item 선택 취소")
                    } else {
                        selectedIndex.value = index
                        onClickButton(index, item)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getColors(selectedIndex.value == index).first,
                    contentColor = getColors(selectedIndex.value == index).second
                ),
                contentPadding = PaddingValues(17.dp, 0.dp)
            ) {
                Text(text = item, fontSize = 14.sp)
            }

            if (index != list.lastIndex) {
                Spacer(Modifier.size(10.dp))
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
    val activeContainerColor = colorResource(com.hs.workation.core.resource.R.color.grey_900)
    val activeContentColor = Color.White
    val containerColor = colorResource(com.hs.workation.core.resource.R.color.grey_400)
    val contentColor = Color.White

    return if (isActivate) {
        Pair(activeContainerColor, activeContentColor)
    }
    else {
        Pair(containerColor, contentColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioButtonPreview() {
    // mockup data
    val list = listOf("+ 10분", "+ 20분", "+ 30분", "+ 1시간", "+ 1시간 30분")

    GroupRadioButton(list) { _, _ -> }
}