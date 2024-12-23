package com.hs.workation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hs.workation.core.model.mock.Timeline

/**
 * 타임라인 목록
 *
 * @param list
 */
@Composable
fun TimelineView(list: List<Timeline>) {
    LazyColumn {
        itemsIndexed(list) { index, timeline ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
            ) {
                // 왼쪽 버티컬 라인
                VerticalLine(index)

                // 오른쪽 콘텐츠
                Column {
                    Text(timeline.title)

                    timeline.items.map {
                        TimelineItem(it)
                    }
                }
            }
        }
    }
}

/**
 * 콘텐츠 내용 영역
 *
 * @param text
 */
@Composable
fun TimelineItem(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 10.dp)
            .background(Color.Yellow, ShapeDefaults.Medium),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}

/**
 * 커스텀 버티컬 라인
 *
 * @param index
 */
@Composable
fun VerticalLine(index: Int) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 텍스트와 원형 아이콘 정렬을 맞추기 위한 스타일
        VerticalDivider(
            Modifier.height(5.dp),
            color = if (index == 0) Color.Transparent else Color.LightGray
        )

        // 원형 아이콘
        Box(Modifier
            .size(7.dp)
            .background(Color.LightGray, ShapeDefaults.ExtraLarge)
        )

        // 버티컬 라인
        VerticalDivider()
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineViewPreview() {
    // mockup data
    val items = listOf("1", "2", "3", "4", "5")
    val list = listOf(
        Timeline("aaaa", items),
        Timeline("bbbb", items),
        Timeline("cccc", items)
    )

    TimelineView(list)
}