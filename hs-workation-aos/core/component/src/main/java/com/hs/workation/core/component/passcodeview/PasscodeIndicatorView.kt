package com.hs.workation.core.component.passcodeview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.hs.workation.core.resource.R

/**
 * Passcode 인디케이터
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
class PasscodeIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), PasscodeIndicatorViewContract {

    // 디폴트 원 갯수
    private var circleCount: Int = 6
    // 채워진 원 갯수
    private var filledCount: Int = 0
    private val circleRadius: Float = 20f
    private val circleSpacing: Float = 40f

    // 채워지지 않은 원 Paint
    private val emptyCirclePaint = Paint().apply {
        color = ContextCompat.getColor(context, com.hs.workation.core.resource.R.color.grey_500)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    // 채워진 원 Paint
    private val filledCirclePaint = Paint().apply {
        color = ContextCompat.getColor(context, com.hs.workation.core.resource.R.color.grey_900)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    /**
     * 최대 원 갯수 설정
     *
     * @param count
     */
    override fun setCircleCount(count: Int) {
        circleCount = count
        invalidate()
    }

    /**
     * 현재 채워진 원 설정
     *
     * @param count
     */
    override fun setFilledCount(count: Int) {
        filledCount = count
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = (circleCount * (circleRadius * 2 + circleSpacing)).toInt() - circleSpacing.toInt()
        val height = (circleRadius * 2).toInt()
        setMeasuredDimension(width, height)
    }

    @SuppressLint("ResourceAsColor")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(ContextCompat.getColor(context, R.color.transparent))

        val startX = circleRadius
        for (i in 0 until circleCount) {
            val x = startX + i * (circleRadius * 2 + circleSpacing)

            val paint = if (i < filledCount) {
                filledCirclePaint
            } else {
                emptyCirclePaint
            }
            canvas.drawCircle(x, circleRadius, circleRadius, paint)
        }
    }
}