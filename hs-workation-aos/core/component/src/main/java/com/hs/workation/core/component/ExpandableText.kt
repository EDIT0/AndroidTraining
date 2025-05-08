package com.hs.workation.core.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

/**
 * 확장 가능한 텍스트 뷰
 * 확장 버튼을 인라인 형식 / 접기 버튼은 블록 형식 으로 보여줌
 * @param text
 * @param maxLines
 */
@Composable
fun ExpandableText(
    text: String,
    maxLines: Int,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    // 텍스트 상태
    val textLayoutState = remember { mutableStateOf<TextLayoutResult?>(null) }

    // 확장 버튼 상태
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    val textLayoutResult = textLayoutState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = maxLines - 1

        // 접은 상태이며 ellipsized 상태일 때 동작
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )

            // 버튼 오프셋 값 설정
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            // 버튼 사이즈 만큼 텍스트 생략
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Column {
        Box {
            Text(
                text = cutText ?: text,
                maxLines = if (expanded) Int.MAX_VALUE else maxLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutState.value = it },
                color = colorResource(com.hs.workation.core.resource.R.color.grey_600)
            )

            if (!expanded) {
                val density = LocalDensity.current
                Text(
                    text = "... 전체보기",
                    onTextLayout = { seeMoreSizeState.value = it.size },
                    modifier = Modifier
                        .then(
                            if (!expanded && seeMoreOffset != null)
                                Modifier.offset(
                                    x = with(density) { seeMoreOffset.x.toDp() },
                                    y = with(density) { seeMoreOffset.y.toDp() },
                                )
                            else
                                Modifier
                        )
                        .clickable {
                            expanded = !expanded
                            cutText = null
                        }
                        .alpha(if (seeMoreOffset != null) 1f else 0f),
                    color = colorResource(com.hs.workation.core.resource.R.color.grey_900)
                )
            }
        }

        if (expanded) {
            Button(
                onClick = {
                    expanded = !expanded
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(com.hs.workation.core.resource.R.color.grey_900)
                ),
                modifier = Modifier.padding(top = 5.dp).height(24.dp),
                contentPadding = PaddingValues(0.dp, 0.dp)
            ) {
                Text(text = "접기")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandableTextPreview() {
    Column {
        // 단문
        ExpandableText("Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            maxLines = 3)

        Spacer(Modifier.size(40.dp))

        // 장문
        ExpandableText("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s," +
                " when an unknown printer took a galley of type and scrambled it to make a type specimen book." +
                " It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged." +
                " It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages," +
                " and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            maxLines = 3)
    }
}