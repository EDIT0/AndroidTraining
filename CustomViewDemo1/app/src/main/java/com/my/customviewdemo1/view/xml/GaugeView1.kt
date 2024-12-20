package com.my.customviewdemo1.view.xml

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.my.customviewdemo1.LogUtil
import com.my.customviewdemo1.R
import com.my.customviewdemo1.ViewSizeUtil

class GaugeView1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rootWidth = 0f
    private var rootHeight = 0f
    private var rootPadding = ViewSizeUtil.dpToPx(context, 10f).toFloat()
    private var centerX = 0f
    private var centerY = 0f

    private var rectF: RectF = RectF()
    private var leftX = 0f
    private var leftY = 0f
    private var rightX = 0f
    private var rightY = 0f
    private var paint: Paint = Paint()
    private var gaugeBarStroke = ViewSizeUtil.dpToPx(context, 15f).toFloat()

    private var gaugeRectF: RectF = RectF()
    private var gaugePaint: Paint = Paint()
    private var gaugePercentage = 0f


    private var text1Paint = Paint()
    private var text1: String? = null
    private var text2Paint = Paint()
    private var text2: String? = null
    private var text3Paint = Paint()
    private var text3: String? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = rootWidth / 2f
        centerY = rootHeight / 2f
        leftX = rootPadding + gaugeBarStroke
        leftY = rootPadding + gaugeBarStroke
        rightX = rootWidth - rootPadding - gaugeBarStroke
        rightY = rootHeight * 2f - rootPadding * 2 - gaugeBarStroke * 2 // 2배를 하는 이유는 원의 반만 사용하기 위해

        rectF = RectF(
            leftX,
            leftY,
            rightX,
            rightY
        )

        paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = gaugeBarStroke
            color = ContextCompat.getColor(context, R.color.teal_200)
        }

        gaugeRectF = RectF(
            leftX,
            leftY,
            rightX,
            rightY
        )

        gaugePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = gaugeBarStroke
            color = ContextCompat.getColor(context, R.color.yellow_600)
        }

        text1Paint = Paint().apply {
            isAntiAlias = true
            textSize = ViewSizeUtil.pxToDp(context, (rightX - leftX) / 5f).toFloat()
            color = ContextCompat.getColor(context, R.color.grey_900)
            textAlign = Paint.Align.CENTER  // 텍스트 중앙 정렬
            typeface = Typeface.DEFAULT_BOLD
        }

        text2Paint = Paint().apply {
            isAntiAlias = true
            textSize = ViewSizeUtil.pxToDp(context, (rightX - leftX) / 6f).toFloat()
            color = ContextCompat.getColor(context, R.color.grey_500)
            textAlign = Paint.Align.CENTER  // 텍스트 중앙 정렬
        }

        text3Paint = Paint().apply {
            isAntiAlias = true
            textSize = ViewSizeUtil.pxToDp(context, (rightX - leftX) / 6f).toFloat()
            color = ContextCompat.getColor(context, R.color.grey_700)
            textAlign = Paint.Align.CENTER  // 텍스트 중앙 정렬
        }

        LogUtil.d_dev("CenterX: ${centerX} CenterY: ${centerY}")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(
            rectF,
            180f,
            180f,
            false, // 부채꼴: true, 호: false
            paint
        )

        canvas.drawArc(
            gaugeRectF,
            180f,
            180f * 0.01f * gaugePercentage,
            false,
            gaugePaint
        )

        val text2Height = text2Paint.descent() - text2Paint.ascent()

        text1?.let { text ->
            canvas.drawText(
                text,
                centerX,
                (rightY / 2) - text2Height - text2Height/2  + rootPadding,
                text1Paint
            )
        }

        text2?.let { text ->
            canvas.drawText(
                text,
                centerX - text2Paint.measureText(text3?:"") / 2,  // 전체 텍스트 중앙 정렬을 위한 위치 조정
                (rightY / 2) + rootPadding,
                text2Paint
            )
        }

        text3?.let { text ->
            canvas.drawText(
                text,
                centerX + text2Paint.measureText(text2?:"") / 2,
                (rightY / 2) + rootPadding,
                text3Paint
            )
        }

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
        rootHeight = width / 2f

        setMeasuredDimension(
            rootWidth.toInt(),
            rootHeight.toInt()
        )
    }

    /**
     * 현재 게이지 퍼센테이지 설정
     *
     * @param percentage 0 ~ 100%
     */
    fun setGaugePercentage(percentage: Float) {
        gaugePercentage = percentage
        invalidate()
    }

    /**
     * 텍스트 설정
     *
     * @param text1 위에서부터 1번째줄 텍스트
     * @param text2 2번째줄 앞 텍스트
     * @param text3 2번째줄 뒤 텍스트
     */
    fun setCenterBottomText(text1: String?, text2: String?, text3: String?) {
        text1?.let {
            this.text1 = it
        }

        text2?.let {
            this.text2 = it
        }

        text3?.let {
            this.text3 = it
        }
    }
}