package com.hs.workation.core.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

/**
 * 이미지 뷰 페이저
 *
 * @param data : 목업 데이터 클래스
 * @param imageHeight : 높이 값
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarouselNavigator(
    data: List<String>,
    imageHeight: Int = 300
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { data.size })

    Box( Modifier.height(imageHeight.dp)) {
        // Prev 버튼
        Row(Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.weight(.1f)) {
                if (pagerState.canScrollBackward) {
                    IconButton(onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage-1)
                        }}
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "prev")
                    }
                }
            }

            // 이미지 뷰 페이저
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight.dp),
                    imageModel = {
                        data[page]
                    },
                    previewPlaceholder = painterResource(id = R.drawable.ic_visibility_off),
                    imageOptions = ImageOptions(
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
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

            // Next 버튼
            Box(Modifier.weight(.1f)) {
                if (pagerState.canScrollForward) {
                    IconButton(onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage+1)
                        }}
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "next")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageCarouselNavigatorPreview() {
    // mockup data
    val data = listOf(
        "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/30d26bb8-153b-473b-b82b-1c8c86062e94.jpeg",
        "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/939f3659-d4a5-4238-ab79-f499d18e81e1.jpeg",
        "https://a0.muscache.com/im/pictures/miso/Hosting-848517573276935309/original/5dfa278f-44ff-4a16-8d08-e4971ecad3cb.jpeg"
    )

    ImageCarouselNavigator(data = data)
}