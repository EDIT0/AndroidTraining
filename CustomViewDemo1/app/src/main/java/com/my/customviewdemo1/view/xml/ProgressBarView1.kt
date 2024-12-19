package com.my.customviewdemo1.view.xml

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.my.customviewdemo1.R
import com.my.customviewdemo1.ViewSizeUtil

class ProgressBarView1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rootPadding = ViewSizeUtil.dpToPx(context, 10f).toFloat()

    private var linePaint = Paint()
    private var lineXLeft = 0f
    private var lineXRight = 0f
    private var lineYPos = 0f
    private var lineHeight = ViewSizeUtil.dpToPx(context, 12f).toFloat()
    private var lineColor = ContextCompat.getColor(context, R.color.grey_500)
    private var lineCorner = ViewSizeUtil.dpToPx(context, 5f).toFloat()

    private var lineChargingPaint = Paint()
    private var lineChargingXLeft = 0f
    private var lineChargingColor = ContextCompat.getColor(context, R.color.grey_800)
    private var chargingValue = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        lineXLeft = rootPadding
        lineXRight = w - rootPadding
        lineYPos = h * 0.5f

        lineChargingXLeft = rootPadding

        linePaint = Paint().apply {
            style = Paint.Style.FILL
            color = lineColor
        }

        lineChargingPaint = Paint().apply {
            style = Paint.Style.FILL
            color = lineChargingColor
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(
            lineXLeft,
            lineYPos - lineHeight / 2,
            lineXRight,
            lineYPos + lineHeight / 2,
            lineCorner,
            lineCorner,
            linePaint
        )

//        if(chargingValue != 0f) {
//            canvas.drawRoundRect(
//                lineChargingXLeft,
//                lineYPos - lineHeight / 2,
//                (lineXRight - lineXLeft) * (chargingValue / 100),
//                lineYPos + lineHeight / 2,
//                lineCorner,
//                lineCorner,
//                lineChargingPaint
//            )
//        }

        if(chargingValue != 0f) {
            val chargingWidth = (lineXRight - lineXLeft) * (chargingValue / 100)
            canvas.drawRoundRect(
                lineChargingXLeft,
                lineYPos - lineHeight / 2,
                lineChargingXLeft + chargingWidth,
                lineYPos + lineHeight / 2,
                lineCorner,
                lineCorner,
                lineChargingPaint
            )
        }

    }

    fun setLineColor(lineBackgroundColor: Int, chargingLineBackgroundColor: Int) {
        lineColor = ContextCompat.getColor(context, lineBackgroundColor)
        lineChargingColor = ContextCompat.getColor(context, chargingLineBackgroundColor)
        invalidate()
    }

    fun setLineCorner(cornerDp: Int) {
        lineCorner = ViewSizeUtil.dpToPx(context, cornerDp.toFloat()).toFloat()
        invalidate()
    }

    fun setLineHeight(heightDp: Int) {
        lineHeight = ViewSizeUtil.dpToPx(context, heightDp.toFloat()).toFloat()
        invalidate()
    }

    fun setChargingValue(value: Float) {
        chargingValue = if(value < 0) {
            0f
        } else if(value > 100) {
            100f
        } else {
            value
        }
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            widthMeasureSpec,
            lineHeight.toInt() + rootPadding.toInt()
        )
    }
}