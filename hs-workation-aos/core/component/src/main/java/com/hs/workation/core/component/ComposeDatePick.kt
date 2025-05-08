package com.hs.workation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hs.workation.core.resource.R
import com.hs.workation.core.util.OnSingleClickListener.onSingleClick

/**
 * @param dateString : 선택된 날짜
 * @param people : 인원 수
 * @param onClickDate : 달력 띄우기
 * @param onClickPeople : 인원 수 스피너 띄우기
 */
@Composable
fun ComposeDatePick(
    dateString: String? = null,
    people: Int? = null,
    onClickDate: () -> Unit,
    onClickPeople: () -> Unit
) {
    val days = dateString?.split(" • ")?.first()
    val nights = dateString?.split(" • ")?.last()

    Row(
        Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(1.dp, colorResource(R.color.grey_300), ShapeDefaults.Medium),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        // 달력 뷰
        Row(
            modifier = Modifier
                .onSingleClick {
                    onClickDate()
                }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Search,
                "search",
                tint = colorResource(R.color.grey_300)
            )

            Text(
                text = days ?: "날짜를 선택 하세요",
                modifier = Modifier.padding(start = 6.dp),
                color = colorResource(R.color.grey_900),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = nights ?: "0박",
                modifier = Modifier
                    .padding(start = 6.dp)
                    .background(
                        color = colorResource(R.color.grey_300),
                        shape = ShapeDefaults.ExtraLarge
                    )
                    .padding(8.dp, 2.dp),
                fontSize = 10.sp,
            )
        }

        VerticalDivider(Modifier.padding(10.dp))

        // 스피너 뷰
        Row(
            modifier = Modifier
                .onSingleClick {
                    onClickPeople()
                }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${people ?: 0}명",
                color = colorResource(R.color.grey_900),
                fontWeight = FontWeight.Bold
            )
            Icon(Icons.Default.KeyboardArrowDown, "expand")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComposeDatePickPreview() {
    Column {
        // 데이터 있을 떄
        ComposeDatePick(
            dateString = "3.19 (화) - 3.20 (수) • 1박",
            people = 1,
            onClickDate = {},
            onClickPeople = {}
        )

        // 데이터 없을 때
        ComposeDatePick(onClickDate = {}, onClickPeople = {})
    }
}