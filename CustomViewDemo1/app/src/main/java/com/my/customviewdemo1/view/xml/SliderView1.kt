package com.my.customviewdemo1.view.xml

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.my.customviewdemo1.LogUtil
import com.my.customviewdemo1.R
import com.my.customviewdemo1.ViewSizeUtil
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class SliderView1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var sliderChangeListener: SliderChangeListener? = null

    // 0.0 ~ 1.0
    private var currentValue = 0.5f

    // 슬라이더가 움직이는 가로 막대
    private var linePaint = Paint()
    private var lineXLeft = 0f
    private var lineXRight = 0f
    private var lineYPos = 0f
    private var lineHeight = ViewSizeUtil.dpToPx(context, 12f).toFloat()
    private var lineColor = ContextCompat.getColor(context, R.color.grey_500)
    private var lineCorner = ViewSizeUtil.dpToPx(context, 5f).toFloat()

    // 컨트롤 원 기준 왼쪽을 담당하는 가로 막대 (전체 가로 막대 위에서 색 변경을 위해 사용된다)
    private var lineChargingPaint = Paint()
    private var lineChargingXLeft = 0f
    private var lineChargingColor = ContextCompat.getColor(context, R.color.grey_800)
    private var isEnableLineChargingBar = true

    // 값 컨트롤 원
    private var controlCirclePaint = Paint()
    private var controlCircleXCenter = 0f
    private var controlCircleYCenter = 0f
    private var controlCircleRadius = ViewSizeUtil.dpToPx(context, 10f).toFloat()
    private var controlCircleColor = ContextCompat.getColor(context, R.color.white)

    // 컨트롤 원의 Stroke (컨트롤 원 위에서 Stroke 색 변경을 위해 사용된다)
    private var controlCircleStrokePaint = Paint()
    private var controlCircleStrokeColor = ContextCompat.getColor(context, R.color.grey_700)
    private var controlCircleStrokeSize = ViewSizeUtil.dpToPx(context, 1f).toFloat()

    private var isDragged = false // 드래그 여부
    private var lastMotionEventX = 0f // 마지막 컨트롤 원의 x위치

    private var rangeFrom = 0f // 시작 범위 값
    private var rangeTo = 100f // 끝 범위 값

    private fun isDownEventInsideControlCircle(event: MotionEvent): Boolean {
        val isDownEvent = event.action == MotionEvent.ACTION_DOWN
        val isInsideControlCircle = event.distanceTo(controlCircleXCenter, controlCircleYCenter) <= controlCircleRadius
//        LogUtil.d_dev("event.action: ${event.action} isDownEvent: ${isDownEvent} isInsideControlCircle: ${isInsideControlCircle}")
        return isDownEvent && isInsideControlCircle
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }
        LogUtil.d_dev("지금 들어온 이벤트: ${event.action} 드래그: ${isDragged}")
        return if(event.action == MotionEvent.ACTION_DOWN) {
            //            LogUtil.d_dev("event.x: ${event.x} / lastMotionEventX: ${lastMotionEventX}")
            val dx = event.x - lastMotionEventX
//            LogUtil.d_dev("dx: ${dx}")
            controlCircleXCenter = if (event.x < lineXLeft) {
                // 왼쪽으로 범위를 벗어났을 경우
                lineXLeft
            } else if (event.x > lineXRight) {
                // 오른쪽으로 범위를 벗어났을 경우
                lineXRight
            } else if (dx < 0) {
                // 왼쪽으로 벗어날 경우, lineXLeft로 넘어가지 않도록 조정
                max(controlCircleXCenter + dx, lineXLeft)
            } else {
                // 오른쪽으로 벗어날 경우, lineXRight로 넘어가지 않도록 조정
                min(controlCircleXCenter + dx, lineXRight)
            }
            // + controlCircleRadius - controlCircleRadius/2
            // - controlCircleRadius + controlCircleRadius/2
            invalidate()
            lastMotionEventX = controlCircleXCenter
            updateValue()
            isDragged = true
            true
        } else if (isDownEventInsideControlCircle(event)) {
            isDragged = true
            lastMotionEventX = event.x
            LogUtil.d_dev("lastMotionEventX: ${lastMotionEventX}")
            true
        } else if (isDragged && event.action == MotionEvent.ACTION_MOVE) {
//            LogUtil.d_dev("event.x: ${event.x} / lastMotionEventX: ${lastMotionEventX}")
            val dx = event.x - lastMotionEventX
//            LogUtil.d_dev("dx: ${dx}")
            controlCircleXCenter = if (event.x < lineXLeft) {
                // 왼쪽으로 범위를 벗어났을 경우
                lineXLeft
            } else if (event.x > lineXRight) {
                // 오른쪽으로 범위를 벗어났을 경우
                lineXRight
            } else if (dx < 0) {
                // 왼쪽으로 벗어날 경우, lineXLeft로 넘어가지 않도록 조정
                max(controlCircleXCenter + dx, lineXLeft)
            } else {
                // 오른쪽으로 벗어날 경우, lineXRight로 넘어가지 않도록 조정
                min(controlCircleXCenter + dx, lineXRight)
            }
            // + controlCircleRadius - controlCircleRadius/2
            // - controlCircleRadius + controlCircleRadius/2
            invalidate()
            lastMotionEventX = controlCircleXCenter
            updateValue()
            true
        } else {
            isDragged = false
            false
        }
    }

    private fun updateValue() {
        val lineWidth = lineXRight - lineXLeft
        val relativeControlCirclePosition = controlCircleXCenter - lineXLeft
        val oldValue = currentValue
        currentValue = 1.0f * relativeControlCirclePosition / lineWidth
        val calValue: Float = (currentValue * (rangeTo - rangeFrom) + rangeFrom)
        LogUtil.d_dev("currentValue: ${currentValue} calValue: ${calValue}")
        if (currentValue != oldValue) {
            sliderChangeListener?.onValueChanged(calValue.toFloat())
            updateControlCirclePosition()
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        LogUtil.d_dev("onSizeChanged() w:${w} h:${h} oldw:${oldw} oldh:${oldh}")

        lineXLeft = controlCircleRadius
        lineXRight = w - controlCircleRadius
        lineYPos = height * 0.5f

        lineChargingXLeft = controlCircleRadius

        linePaint = Paint().apply {
            style = Paint.Style.FILL
            strokeWidth = ViewSizeUtil.dpToPx(context, 1f).toFloat()
            color = lineColor
        }

        lineChargingPaint = Paint().apply {
            style = Paint.Style.FILL
            strokeWidth = ViewSizeUtil.dpToPx(context, 1f).toFloat()
            color = lineChargingColor
        }

        controlCirclePaint = Paint().apply {
            style = Paint.Style.FILL
            strokeWidth = ViewSizeUtil.dpToPx(context, 1f).toFloat()
            color = controlCircleColor
        }

        controlCircleStrokePaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = controlCircleStrokeSize
            color = controlCircleStrokeColor
        }

        updateControlCirclePosition()
    }

    private fun updateControlCirclePosition() {
        controlCircleXCenter = lineXLeft + currentValue * (lineXRight - lineXLeft) // ex) 2 - 9 사이, 2 + 0.5(변경값) * (9 - 2) = 5.5
        controlCircleYCenter = lineYPos
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 범위 선 그리기
//        canvas.drawLine(
//            lineXLeft,
//            lineYPos,
//            lineXRight,
//            lineYPos,
//            linePaint
//        )
        canvas.drawRoundRect(
            lineXLeft,
            lineYPos - lineHeight / 2,
            lineXRight,
            lineYPos + lineHeight / 2,
            lineCorner,
            lineCorner,
            linePaint
        )

        if(isEnableLineChargingBar) {
            canvas.drawRoundRect(
                lineChargingXLeft,
                lineYPos - lineHeight / 2,
                controlCircleXCenter + controlCircleRadius / 2,
                lineYPos + lineHeight / 2,
                lineCorner,
                lineCorner,
                lineChargingPaint
            )
        }


        // 컨트롤 원
        canvas.drawCircle(
            controlCircleXCenter,
            controlCircleYCenter,
            controlCircleRadius,
            controlCirclePaint
        )

        canvas.drawCircle(
            controlCircleXCenter,
            controlCircleYCenter,
            controlCircleRadius,
            controlCircleStrokePaint
        )
    }

    fun MotionEvent.distanceTo(x: Float, y: Float): Float {
//        LogUtil.d_dev("${this.x}/${this.y} || ${x}/${y} || ${pointsDistance(this.x, this.y, x, y)}" )
        return pointsDistance(this.x, this.y, x, y)
    }

    fun pointsDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = x1 - x2
        val dy = y1 - y2
        return sqrt(dx * dx + dy * dy)
    }

    fun setRange(from: Float, to: Float) {
        rangeFrom = from
        rangeTo = to
    }

    /**
     * 막대 설정
     *
     * @param color 색
     * @param heightDp 막대 높이
     * @param lineCornerDp 막대 코너 라운드
     */
    fun setLine(color: Int, heightDp: Int, lineCornerDp: Int) {
        lineColor = ContextCompat.getColor(context, color)
        lineHeight = ViewSizeUtil.dpToPx(context, heightDp.toFloat()).toFloat()
        lineCorner = ViewSizeUtil.dpToPx(context, lineCornerDp.toFloat()).toFloat()
    }

    /**
     * 컨트롤 원 경계선 설정
     *
     * @param color 색
     * @param size 사이즈
     */
    fun setControlCircleStroke(color: Int, sizeDp: Float) {
        controlCircleStrokeColor = ContextCompat.getColor(context, color)
        controlCircleStrokeSize = ViewSizeUtil.dpToPx(context, sizeDp).toFloat()
    }

    /**
     * 컨트롤 원 색 설정
     *
     * @param color 색
     */
    fun setControlCircleColor(color: Int) {
        controlCircleColor = ContextCompat.getColor(context, color)
    }

    /**
     * 컨트롤 원 기준 왼쪽 막대 채워지는 바 설정
     *
     * @param isEnable 채워지는 바 사용 여부
     * @param color 색
     */
    fun setLineChargingColor(isEnable: Boolean, color: Int) {
        isEnableLineChargingBar = isEnable
        lineChargingColor = ContextCompat.getColor(context, color)
    }

    /**
     * 컨트롤 원 크기 결정
     *
     * @param dp 반지름 단위 dp
     */
    fun setControlCircleRadius(dp: Int) {
        controlCircleRadius = ViewSizeUtil.dpToPx(context, dp.toFloat()).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            widthMeasureSpec,
            controlCircleRadius.toInt() * 3
        )
    }
}

interface SliderChangeListener {
    fun onValueChanged(value: Float)
}