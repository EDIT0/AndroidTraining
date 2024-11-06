package com.my.customviewdemo1.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.my.customviewdemo1.R
import kotlinx.coroutines.delay

@Composable
fun LogoView(
    modifier: Modifier = Modifier,
    imageResId: Int,
) {

    val angleDuration = 3000
    val lineEndYDuration = 3000
    val lineAlphaDuration = 1000
    val scaleDuration = 1300

    var sweepAngle by remember { mutableStateOf(0f) } // 0f로 초기화 후 사용
    LaunchedEffect(Unit) {
        sweepAngle = 360f
    }
    val animatedSweepAngle by animateFloatAsState(
        targetValue = sweepAngle,
        animationSpec = tween(durationMillis = angleDuration)
    )

    var lineEndY by remember { mutableStateOf(0f) } // 0f로 초기화 후 사용
    LaunchedEffect(Unit) {
        lineEndY = 1f
    }
    val animatedLineEndY by animateFloatAsState(
        targetValue = lineEndY,
        animationSpec = tween(durationMillis = lineEndYDuration, delayMillis = angleDuration)
    )
    val lineAlpha by animateFloatAsState(
        targetValue = if (lineEndY == 1f) 1f else 0f,
        animationSpec = tween(durationMillis = lineAlphaDuration, delayMillis = angleDuration)
    )

    var targetScale by remember { mutableStateOf(1f) }
    LaunchedEffect(Unit) {
        delay(angleDuration + lineEndYDuration.toLong())
        targetScale = 1.5f
        delay(1500)
        targetScale = 0.9f
        delay(1000L)
        targetScale = 1.0f
    }
    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(
            durationMillis = scaleDuration,
            delayMillis = 0,
        ),
        )

    var rotationAngle by remember {
        mutableStateOf(360f)
    }
    LaunchedEffect(Unit) {
        rotationAngle = 0f
    }
    val animatedRotationAngle by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 3000)
    )

    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(imageResId) {
        bitmap = vectorToBitmap(context, imageResId)
    }

    Canvas(
        modifier = modifier
            .scale(scale)
            .then(modifier), // 기존 modifier 유지
        onDraw = {
            val strokeWidth = 50f
            val radius = (size.width / 4)
            val center = Offset(size.width / 2, size.height / 2)

            drawArc(
                color = Color.Yellow,
                startAngle = 180f,
                sweepAngle = animatedSweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius / 2, center.y - radius / 2),
                size = Size(1 * (size.width / 4), 1 * (size.width / 4)),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )

            drawLine(
                color = Color.Blue.copy(alpha = lineAlpha),
                start = Offset(center.x - radius / 2, center.y),
                end = Offset(center.x - radius / 2, center.y + radius * animatedLineEndY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            bitmap?.let { bmp ->
                val imageSize = 30.dp.toPx() // 원하는 크기
                val srcRect = Rect(0, 0, bmp.width, bmp.height) // 원본 비트맵 크기
                val destRect = Rect(
                    (center.x - imageSize / 2).toInt(),
                    (center.y - imageSize / 2).toInt(),
                    (center.x + imageSize / 2).toInt(),
                    (center.y + imageSize / 2).toInt()
                )

                drawIntoCanvas { canvas ->
                    // 이미지 회전
//                    canvas.save() // 현재 상태 저장
                    canvas.rotate(animatedRotationAngle, center.x, center.y) // 지정된 각도로 회전
                    canvas.nativeCanvas.drawBitmap(bmp, srcRect, destRect, Paint())
//                    canvas.restore() // 원래 상태로 복구
                }
            }

            drawLine(
                color = Color.Gray,
                start = Offset(center.x, center.y),
                end = Offset(center.x, center.y),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
    )
}

// VectorDrawable을 Bitmap으로 변환하는 함수
fun vectorToBitmap(context: Context, drawableResId: Int): Bitmap? {
    val drawable = ResourcesCompat.getDrawable(context.resources, drawableResId, null)
    return if (drawable is android.graphics.drawable.VectorDrawable) {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = android.graphics.Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    } else {
        null
    }
}

@Preview
@Composable
fun LogoViewPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LogoView(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(0.5f),
            imageResId = R.drawable.ic_launcher_background
        )
    }
}