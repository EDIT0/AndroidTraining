package com.my.customviewdemo1.view.xml

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.my.customviewdemo1.R
import com.my.customviewdemo1.ViewSizeUtil

class PercentageBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var percentage: Float = 0f
    private var barWidth: Float = 40f
    private var barColor: Int = ContextCompat.getColor(context, R.color.grey_500)

    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = barColor
    }
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.black)
            strokeWidth = 2f
    }

    fun setPercentage(value: Float) {
        percentage = value.coerceIn(0f, 100f)
        invalidate()
    }

    fun setBarWidth(width: Float) {
        barWidth = ViewSizeUtil.dpToPx(context, width).toFloat()
        invalidate()
    }

    fun setBarColor(color: Int) {
        barColor = color
        barPaint.color = ContextCompat.getColor(context, color)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val barHeight = height * (percentage / 100)
        val barBottom = height.toFloat() - 2f
        val startX = (width - barWidth) / 2

        canvas.drawRect(
            startX,
            barBottom - barHeight,
            startX + barWidth,
            barBottom,
            barPaint
        )

        canvas.drawLine(
            0f,
            height.toFloat(),
            width.toFloat(),
            height.toFloat(),
            linePaint
        )
    }
}