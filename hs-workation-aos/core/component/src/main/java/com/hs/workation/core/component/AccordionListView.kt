package com.hs.workation.core.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hs.workation.core.model.mock.AccordionListItem
import com.hs.workation.core.util.ViewSizeUtil

/**
 * Accordion List View
 *
 * @param modifier
 * @param title 박스 제목
 * @param infoList
 * @param isShowTitleAndListDivider 박스 제목과 리스트 사이 Divider 유무 (true: 있음, false: 없음)
 * @param isShowListDivider 리스트 아이템 항목 별 Divider 유뮤 (true: 있음, false: 없음)
 * @param cornerRadius
 * @param elevation
 * @param borderColor 경계선 색
 * @param backgroundColor 전체 배경 색
 * @param titleTextSize 박스 제목 텍스트 사이즈
 * @param listTitleTextSize 리스트 텍스트 사이즈
 * @param titleTextColor 박스 제목 텍스트 색
 * @param listTitleTextColor 리스트 텍스트 색
 */
@Composable
fun AccordionListView(
    modifier: Modifier = Modifier,
    title: String,
    infoList: List<AccordionListItem>,
    isShowTitleAndListDivider: Boolean,
    isShowListDivider: Boolean,
    cornerRadius: Int = 10,
    elevation: Int = 2,
    borderColor: Int = com.hs.workation.core.resource.R.color.grey_500,
    backgroundColor: Int = com.hs.workation.core.resource.R.color.white,
    titleTextSize: Int,
    listTitleTextSize: Int,
    titleTextColor: Int,
    listTitleTextColor: Int,
    iconColor: Int
) {

    val isShowList = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(cornerRadius.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = borderColor)
        ),
        elevation = CardDefaults.cardElevation(elevation.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = backgroundColor),
        ),
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    isShowList.value = !isShowList.value
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        end = 5.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    )
                    .weight(1f),
                text = title,
                style = TextStyle(
                    fontSize = ViewSizeUtil.dpToSp(dp = titleTextSize.dp),
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                color = colorResource(id = titleTextColor)
            )
            Icon(
                imageVector = if (isShowList.value) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                tint = colorResource(id = iconColor),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.width(20.dp))
        }


        AnimatedVisibility(
            visible = isShowList.value
        ) {
            LazyColumn() {
                if (isShowTitleAndListDivider) {
                    item {
                        Row {
                            Spacer(
                                modifier = Modifier
                                    .width(20.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(0.5.dp)
                                    .background(color = colorResource(id = com.hs.workation.core.resource.R.color.grey_500))
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(20.dp)
                            )
                        }
                    }
                }
                itemsIndexed(infoList) { index, item ->
                    Column {
                        Row {
                            Spacer(
                                modifier = Modifier
                                    .width(20.dp)
                            )

                            Text(
                                modifier = Modifier
                                    .padding(vertical = 10.dp),
                                text = item.title,
                                style = TextStyle(
                                    fontSize = ViewSizeUtil.dpToSp(dp = listTitleTextSize.dp),
                                    fontWeight = FontWeight.Normal
                                ),
                                maxLines = 1,
                                color = colorResource(id = listTitleTextColor)
                            )

                            Spacer(
                                modifier = Modifier
                                    .width(20.dp)
                            )
                        }
                        if (isShowListDivider) {
                            Row {
                                Spacer(
                                    modifier = Modifier
                                        .width(20.dp)
                                )
                                Spacer(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(0.5.dp)
                                        .background(color = colorResource(id = com.hs.workation.core.resource.R.color.grey_400))
                                )
                                Spacer(
                                    modifier = Modifier
                                        .width(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AccordionListViewPreview() {
    AccordionListView(
        title = "Main Title",
        infoList = listOf(
            AccordionListItem(title = "1번"),
            AccordionListItem(title = "2번"),
            AccordionListItem(title = "3번")
        ),
        isShowTitleAndListDivider = true,
        isShowListDivider = true,
        titleTextSize = 10,
        listTitleTextSize = 10,
        titleTextColor = com.hs.workation.core.resource.R.color.grey_600,
        listTitleTextColor = com.hs.workation.core.resource.R.color.grey_600,
        iconColor = com.hs.workation.core.resource.R.color.grey_600
    )
}