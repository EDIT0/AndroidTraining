package com.my.customviewdemo1.view

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BatteryView(
    modifier: Modifier = Modifier,
    percentage: Int = 30
) {

    val animatedSweepAngle by animateFloatAsState(
        targetValue = 360f * percentage / 100,
        animationSpec = tween(durationMillis = 500)
    )

    Canvas(
        modifier = modifier
            .aspectRatio(1f),
        onDraw = {

            val gradient = Brush.horizontalGradient(
                colors = listOf(
                    Color.Red,
                    Color(0xFFFFA500),
                    Color.Yellow,
                    Color(0xFFADFF2F),
                    Color.Green
                )
            )

            val strokeWidth = 30f
            val radius = (size.width / 4) - (strokeWidth / 4)
            val center = Offset(size.width / 2, size.height / 2)
            val centerTextSize = 150f

            drawArc(
                color = Color.LightGray,
                startAngle = 270f,
                sweepAngle = 360f,
                useCenter = false,
                size = Size(1 * (size.width / 2 - strokeWidth), 1 * (size.width / 2 - strokeWidth)),
                style = Stroke(width = strokeWidth),
                alpha = 0.5f,
                topLeft = Offset(center.x - radius, center.y - radius),
            )

            drawArc(
//                color = Color.Green,
                brush = gradient,
                startAngle = 270f,
//                sweepAngle = 360f * percentage / 100,
                sweepAngle = animatedSweepAngle,
                useCenter = false,
                size = Size(1 * (size.width / 2 - strokeWidth), 1 * (size.width / 2 - strokeWidth)),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                alpha = 0.5f,
                topLeft = Offset(center.x - radius, center.y - radius),
            )

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${percentage}%",
                    center.x,
                    center.y - (
                            Paint().apply {
                                textSize = centerTextSize
                            }.fontMetrics.ascent + Paint().apply {
                                textSize = centerTextSize
                            }.fontMetrics.descent) / 2,
                    Paint().apply {
                        color = if(true) {
                            android.graphics.Color.GREEN
                        } else {
                            android.graphics.Color.GRAY
                        }
                        textSize = centerTextSize
                        textAlign = Paint.Align.CENTER
                        isFakeBoldText = true
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun BatteryViewPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BatteryView(
            modifier = Modifier
                .wrapContentWidth()
                .aspectRatio(1.5f)
        )
    }
}