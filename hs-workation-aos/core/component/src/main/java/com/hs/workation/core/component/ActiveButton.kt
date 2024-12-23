package com.hs.workation.core.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hs.workation.core.util.ViewSizeUtil

/**
 * 활성/비활성 버튼
 *
 * @param modifier
 * @param onButtonClick
 * @param isEnable
 * @param title
 * @param titleColor first: inactive second: active
 * @param titleSize
 * @param elevation
 * @param cornerRadius
 * @param backgroundColor first: inactive second: active
 * @param borderStroke
 * @param borderStrokeColor first: inactive second: active
 */
@Composable
fun ActiveButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    isEnable: Boolean = true,
    title: String,
    titleColor: Pair<Int, Int>,
    titleSize: Float,
    elevation: Int = 0,
    cornerRadius: Float = 0f,
    backgroundColor: Pair<Int, Int>,
    borderStroke: Float = 0f,
    borderStrokeColor: Pair<Int, Int>
) {

//    Column {
        Card(
            modifier = modifier
                .then(modifier),
            enabled = isEnable,
            elevation = CardDefaults.cardElevation(elevation.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = if(isEnable) { backgroundColor.second } else { backgroundColor.first }),
            ),
            shape = RoundedCornerShape(cornerRadius.dp),
            border = BorderStroke(
                width = borderStroke.dp,
                color = colorResource(id = if(isEnable) {borderStrokeColor.second} else {borderStrokeColor.first})
            ),
            onClick = {
                onButtonClick.invoke()
            },
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = if(isEnable) { backgroundColor.second } else { backgroundColor.first }))
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                text = title,
                textAlign = TextAlign.Center,
                color = colorResource(id = if(isEnable) {titleColor.second} else {titleColor.first}),
                style = TextStyle(fontSize = ViewSizeUtil.dpToSp(dp = titleSize.dp), fontWeight = FontWeight.SemiBold)
            )
        }
//    }


}

@Preview(showBackground = true)
@Composable
private fun ActiveButtonPreview() {
    ActiveButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        onButtonClick = {

        },
        isEnable = true,
        title = "Button Title",
        titleColor = Pair(com.hs.workation.core.common.R.color.grey_600, com.hs.workation.core.common.R.color.blue_500),
        titleSize = 16f,
        elevation = 3,
        cornerRadius = 5f,
        backgroundColor = Pair(com.hs.workation.core.common.R.color.grey_400, com.hs.workation.core.common.R.color.white),
        borderStroke = 0.5f,
        borderStrokeColor = Pair(com.hs.workation.core.common.R.color.grey_500, com.hs.workation.core.common.R.color.teal_200)
    )
}