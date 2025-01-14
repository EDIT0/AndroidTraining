package com.my.customviewdemo1.view.xml

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.my.customviewdemo1.R
import com.my.customviewdemo1.ViewSizeUtil

class ProgressIconButtonView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var rectF = RectF()
    private var progress = 0f
    private var centerIcon: Drawable? = null

    private var rootPadding = ViewSizeUtil.dpToPx(context, 10f).toFloat()
    private var rootWidth = 0f
    private var rootHeight = 0f

    private var centerX = 0f
    private var centerY = 0f
    private var leftX = 0f
    private var leftY = 0f
    private var rightX = 0f
    private var rightY = 0f

    private val stroke = ViewSizeUtil.dpToPx(context, 8f).toFloat()
    private var radius = 0f

    init {
        centerIcon = ContextCompat.getDrawable(context, R.drawable.ic_on_off_white_32)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        // width 결정
        width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize // match_parent나 정확한 수치
            MeasureSpec.AT_MOST -> widthSize // wrap_content
            else -> 400 // 기본값
        }

        // height 결정 (width의 1/2 비율)
        height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize // match_parent나 정확한 수치
            else -> (width / 2) + (rootPadding * 2).toInt() // width의 절반 + 패딩
        }

        rootWidth = width.toFloat()
        rootHeight = width.toFloat()

        setMeasuredDimension(
            rootWidth.toInt(),
            rootHeight.toInt()
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = rootWidth / 2f
        centerY = rootHeight / 2f
        leftX = rootPadding + stroke
        leftY = rootPadding + stroke
        rightX = rootWidth - rootPadding - stroke
        rightY = rootHeight - rootPadding - stroke

        rectF = RectF(
            leftX,
            leftY,
            rightX,
            rightY
        )

        radius = (rightX - leftX) / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 경계선 페인트 설정
        if(progress > 0) {
            paint.apply {
                style = Paint.Style.STROKE
                strokeWidth = stroke
                color = ContextCompat.getColor(context, R.color.grey_700)
            }
            canvas.drawCircle(centerX, centerY, radius, paint)
        }


        // 경계선 프로그래스 페인트 설정
        paint.apply {
            color = ContextCompat.getColor(context, R.color.light_blue_400)
            strokeWidth = stroke
            strokeCap = Paint.Cap.ROUND
        }
        rectF.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
        canvas.drawArc(
            rectF,
            -90f,
            progress * 360f / 100,
            false,
            paint
        )

        // 원 배경화면 페인트
        paint.apply {
            color = ContextCompat.getColor(context, R.color.blue_900)
            style = Paint.Style.FILL
        }
        canvas.drawCircle(centerX, centerY, radius - (stroke/2).toFloat(), paint)

        // 중앙 아이콘 그리기
        centerIcon?.let {
            val iconSize = radius * 0.5f
            it.setBounds(
                (centerX - iconSize).toInt(),
                (centerY - iconSize).toInt(),
                (centerX + iconSize).toInt(),
                (centerY + iconSize).toInt()
            )
            it.draw(canvas)
        }
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 100f)
        invalidate()

        if(value == 100f) {
            progress = 0f
            centerIcon = ContextCompat.getDrawable(context, R.drawable.ic_on_off_white_32)
        } else {
            centerIcon = ContextCompat.getDrawable(context, R.drawable.ic_on_off_alpha_50_white_32)
        }
    }

}
