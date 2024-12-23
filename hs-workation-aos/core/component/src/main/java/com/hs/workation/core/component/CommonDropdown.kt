package com.hs.workation.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.hs.workation.core.model.mock.DropdownMenuItem
import com.hs.workation.core.util.ViewSizeUtil

/**
 * 드롭박스
 *
 * @param modifier modifier에 padding 사용 금지
 * @param maxWidth 너비 셋팅
 * @param onSelectedListener list index 와 선택된 드롭다운메뉴 아이템
 * @param initTitle 선택되기 전 초기화 문구
 * @param optionMenuList 드롭다운메뉴 아이템 리스트
 * @param cornerRadius 드롭다운메뉴 헤더 코너
 * @param elevation
 * @param titleBackgroundColor 드롭다운메뉴 헤더 백그라운드 색
 * @param optionMenuBackgroundColor 드롭다운메뉴 아이템 백그라운드 색
 * @param borderColor 경계선 색
 * @param titleTextSize
 * @param titleTextColor
 * @param optionMenuTextSize
 * @param optionMenuTextColor
 * @param arrowIconColor 오른쪽 화살표 색
 */
@Composable
fun CommonDropdown(
    modifier: Modifier = Modifier,
    maxWidth: Int,
    onSelectedListener: (Int, DropdownMenuItem) -> Unit,
    initTitle: String,
    optionMenuList: List<DropdownMenuItem>,
    cornerRadius: Int,
    elevation: Int = 0,
    titleBackgroundColor: Int,
    optionMenuBackgroundColor: Int,
    borderColor:Int,
    titleTextSize: Int = 16,
    titleTextColor: Int = com.hs.workation.core.common.R.color.black,
    optionMenuTextSize: Int = 16,
    optionMenuTextColor: Int = com.hs.workation.core.common.R.color.black,
    arrowIconColor: Int? = null
) {
    // 드롭박스메뉴 아이템 열림/닫힘
    val expandedContent = remember {
        mutableStateOf(false)
    }
    // 선택된 옵션
    val selectedOption = remember {
        mutableStateOf(initTitle)
    }
    // 드롭박스메뉴 아이템 너비 지정
    val pxWidth = remember {
        mutableStateOf(0)
    }

    Card(
        modifier = modifier
            .then(modifier)
            .widthIn(max = maxWidth.dp)
            .height(IntrinsicSize.Max)
            .onGloballyPositioned { coordinates ->
                pxWidth.value = coordinates.size.width // pixel
            },
        shape = RoundedCornerShape(cornerRadius.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = borderColor)
        ),
        elevation = CardDefaults.cardElevation(elevation.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = titleBackgroundColor),
        ),
        onClick = {
            expandedContent.value = !expandedContent.value
        }
    ) {
        Row(
            modifier = modifier
//                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = modifier
                    .weight(1f),
                text = selectedOption.value,
                style = TextStyle(fontSize = ViewSizeUtil.dpToSp(dp = titleTextSize.dp)),
                color = colorResource(id = titleTextColor),
                maxLines = 1
            )
            Icon(
                imageVector = if(expandedContent.value) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                tint = if(arrowIconColor == null) {
                    colorResource(id = com.hs.workation.core.common.R.color.grey_500)
                } else {
                    colorResource(id = arrowIconColor)
                },
                contentDescription = ""
            )
        }


        DropdownMenu(
            expanded = expandedContent.value,
            onDismissRequest = {
                expandedContent.value = false
            },
            modifier = modifier
                .width(ViewSizeUtil.pxToDp(LocalContext.current, pxWidth.value.toFloat()).dp)
                .background(
                    color = colorResource(id = optionMenuBackgroundColor)
                ),
            offset = DpOffset(0.dp, 0.dp),
        ) {
            for(i in 0 until optionMenuList.size) {
                // Row는 커스텀용
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedOption.value = optionMenuList[i].menu
                            onSelectedListener.invoke(i, optionMenuList[i])
                            expandedContent.value = false
                        }
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = optionMenuList[i].menu,
                        style = TextStyle(fontSize = ViewSizeUtil.dpToSp(dp = optionMenuTextSize.dp)),
                        color = colorResource(id = optionMenuTextColor)
                    )
                }
                // 드롭다운메뉴아이템으로 설정 가능
//                DropdownMenuItem(
//                    text = {
//                        Text(
//                            text = optionMenuList[i].menu,
//                            style = TextStyle(fontSize = ViewSizeUtil.dpToSp(dp = optionMenuTextSize.dp)),
//                            color = colorResource(id = optionMenuTextColor)
//                        )
//                    },
//                    onClick = {
//                        selectedOption.value = optionMenuList[i].menu
//                        onSelectedListener.invoke(i, optionMenuList[i])
//                        expandedContent.value = false
//                    }
//                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CommonDropdownPreview() {
    CommonDropdown(
        maxWidth = 300,
        onSelectedListener = { index, dropdownMenu ->

        },
        initTitle = "Init Title",
        optionMenuList = listOf(DropdownMenuItem(menu = "Option 1"), DropdownMenuItem(menu = "Option 2"), DropdownMenuItem(menu = "Option 3")),
        cornerRadius = 8,
        titleBackgroundColor = com.hs.workation.core.common.R.color.grey_400,
        optionMenuBackgroundColor = com.hs.workation.core.common.R.color.yellow_200,
        borderColor = com.hs.workation.core.common.R.color.grey_700,
    )
}