package com.my.animdemo1

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class BatteryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var percentage: Int = 30
    private var sweepAngle: Float = 0f
    private val animator = ValueAnimator()

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
        color = Color.GREEN
    }

    private val backgroundArcPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = Color.LTGRAY
        alpha = 128 // 50% transparency
    }

    private val gradientColors = intArrayOf(
        Color.RED,
        Color.rgb(255, 165, 0), // Orange
        Color.YELLOW,
        Color.rgb(173, 255, 47), // GreenYellow
        Color.GREEN
    )

    private var rectF: RectF = RectF()
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var radius: Float = 0f
    private var strokeWidth: Float = 0f

    init {
        setupAnimator()
    }

    private fun setupAnimator() {
        animator.duration = 500
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            sweepAngle = animation.animatedValue as Float
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = w / 2f
        centerY = h / 2f
        strokeWidth = w / 15f
        radius = (w / 4f) - (strokeWidth / 4f)

        paint.strokeWidth = strokeWidth
        backgroundArcPaint.strokeWidth = strokeWidth
        textPaint.textSize = w / 4f

        // Update gradient
        val gradient = LinearGradient(
            0f, 0f, w.toFloat(), 0f,
            gradientColors,
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = gradient

        // Update drawing bounds
        val size = (w / 2f - strokeWidth)
        rectF.set(
            centerX - radius,
            centerY - radius,
            centerX - radius + size,
            centerY - radius + size
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background arc
        canvas.drawArc(rectF, 270f, 360f, false, backgroundArcPaint)

        // Draw progress arc
        canvas.drawArc(rectF, 270f, sweepAngle, false, paint)

        // Draw percentage text
        val textHeight = (textPaint.descent() + textPaint.ascent()) / 2f
        canvas.drawText(
            "$percentage%",
            centerX,
            centerY - textHeight,
            textPaint
        )
    }

    fun setPercentage(newPercentage: Int) {
        percentage = newPercentage.coerceIn(0, 100)
        animator.setFloatValues(sweepAngle, 360f * percentage / 100)
        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(size, size)
    }
}