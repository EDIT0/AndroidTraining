package com.hs.workation.core.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hs.workation.core.model.mock.SpaceInfo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

/**
 * 이미지 상세 모달
 *
 * @param initialPage
 * @param data
 * @param dismiss
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageModal(
    initialPage: Int,
    data: SpaceInfo,
    dismiss: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = initialPage) {
        data.thumbnails.size
    }

    Dialog(
        onDismissRequest = { dismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.BottomCenter
        ) {
            // 이미지 뷰 페이저
            HorizontalPager(state = pagerState) { page ->
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = {
                        data.thumbnails[page]
                    },
                    previewPlaceholder = painterResource(id = R.drawable.ic_visibility_off),
                    imageOptions = ImageOptions(
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit
                    ),
                    requestOptions = {
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    },
                    loading = {
                        ImageLoadingView()
                    },
                    failure = {
                        ImageEmptyView()
                    }
                )
            }

            // 인디케이터
            if (pagerState.pageCount > 1) {
                Row(
                    Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val width = if (pagerState.currentPage == iteration) 14.dp else 4.dp

                        val color =
                            if (pagerState.currentPage == iteration) colorResource(com.hs.workation.core.common.R.color.white)
                            else colorResource(com.hs.workation.core.common.R.color.whiteAlpha60)

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 1.5.dp)
                                .background(color, CircleShape)
                                .animateContentSize()
                                .width(width)
                                .height(4.dp)
                        )
                    }
                }
            }
        }

        // 닫기 버튼
        IconButton(
            onClick = { dismiss() },
            colors = IconButtonDefaults.iconButtonColors(containerColor = colorResource(com.hs.workation.core.common.R.color.grey_900))
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun ImageModalPreview() {
    val pagerState = rememberPagerState { 3 }

    // mockup data
    val data = SpaceInfo(
        name = "다목적홀",
        description = "수용가능 30인",
        pricePerUnit = 10000,
        unit = "30분",
        thumbnails = listOf(
            "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/30d26bb8-153b-473b-b82b-1c8c86062e94.jpeg",
            "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/939f3659-d4a5-4238-ab79-f499d18e81e1.jpeg",
            "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/5dfa278f-44ff-4a16-8d08-e4971ecad3cb.jpeg"
        )
    )

    ImageModal(3, data) {}
}