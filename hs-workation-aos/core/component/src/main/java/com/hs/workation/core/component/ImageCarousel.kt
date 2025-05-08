package com.hs.workation.core.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hs.workation.core.model.mock.SpaceInfo
import com.hs.workation.core.util.NumberFormatter.currencyFormatter
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.hs.workation.core.resource.R

/**
 * 이미지 뷰 페이저
 *
 * @param data : 목업 데이터 클래스
 * @param imageHeight : 높이 값
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(
    data: SpaceInfo,
    imageHeight: Int = 400
) {
    val pagerState = rememberPagerState(pageCount = { data.thumbnails.size })
    val isShowImageModal = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.height(imageHeight.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 이미지 뷰 페이저
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.clip(ShapeDefaults.ExtraLarge)
        ) { page ->
            GlideImage(
                modifier = Modifier
                    .clickable {
                        isShowImageModal.value = true
                    }
                    .height(imageHeight.dp),
                imageModel = {
                    data.thumbnails[page]
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

        // 페이저 인디케이터
        Column(
            Modifier
                .padding(14.dp)
                .background(
                    colorResource(com.hs.workation.core.resource.R.color.whiteAlpha60),
                    ShapeDefaults.Large
                )
                .padding(14.dp)
        ) {
            // 커스텀으로 띄울 정보 뷰
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(6f)) {
                    Text(data.name, fontSize = 18.sp)
                    Text(data.description)
                }

                Column(
                    modifier = Modifier.weight(4f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text("${data.pricePerUnit.currencyFormatter()}원", fontSize = 18.sp)
                    Text("/ ${data.unit}", fontSize = 12.sp, color = colorResource(com.hs.workation.core.resource.R.color.grey_600))
                }
            }

            // 인디케이터
            if (data.thumbnails.size > 1) {
                Row(
                    Modifier
                        .height(14.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(data.thumbnails.size) { iteration ->
                        val width = if (pagerState.currentPage == iteration) 14.dp else 4.dp

                        val color = if (pagerState.currentPage == iteration) colorResource(com.hs.workation.core.resource.R.color.white)
                            else colorResource(com.hs.workation.core.resource.R.color.whiteAlpha60)

                        Box(modifier = Modifier
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
    }

    // 이미지 상세 모달
    if (isShowImageModal.value) {
        ImageModal(
            initialPage = pagerState.currentPage,
            data = data,
            dismiss = { isShowImageModal.value = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageCarouselPreview() {
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

    ImageCarousel(data = data)
}