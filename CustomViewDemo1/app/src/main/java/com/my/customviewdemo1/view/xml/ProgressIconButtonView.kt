package com.my.customviewdemo1.view.xml

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.my.customviewdemo1.LogUtil
import com.my.customviewdemo1.R
import com.my.customviewdemo1.ViewSizeUtil

class ProgressIconButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var centerX: Float = 0f
    private var centerY: Float = 0f

    /**
     * 아이콘
     */
    private var iconDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_rounded_lock_24)!!
    private var iconSize: Int = ViewSizeUtil.dpToPx(context, 24f).toInt() // 아이콘 크기 설정
    private var iconRectPadding = ViewSizeUtil.dpToPx(context, 10f) // 아이콘으로부터 value만큼 padding
    private var unselectedIcon: Drawable? = null
    private var selectedIcon: Drawable? = null

    /**
     * 아이콘 감싸는 Rect
     */
    private var iconRectF = RectF() // 아이콘을 감싸는 범위
    private var iconRectColor = ContextCompat.getColor(context, R.color.grey_700)
    private var iconRectPaint = Paint()

    /**
     * 프로그래스 백그라운드 사각형
     * */
    private val progressBgRectF = RectF()
    private var progressBgRectPadding = ViewSizeUtil.dpToPx(context, 13f) // 아이콘으로부터 value만큼 padding
    private var progressBgRectColor = ContextCompat.getColor(context, R.color.grey_400)
    private var progressBgRectPaint = Paint()

    /**
     * 프로그래스 사각형
     */
    private val progressRectF = RectF() // 프로그래스 아이콘을 감싸는 범위
    private var progressRectPadding = ViewSizeUtil.dpToPx(context, 13f) // 아이콘으로부터 value만큼 padding
    private var progressRectColor = ContextCompat.getColor(context, R.color.black)
    private var progressRectPaint = Paint()

    private var percentage: Float = 0f
    private var progressAutoAnimator: ValueAnimator? = null // 특정 시간동안만 동작
    private var progressAnimator: ValueAnimator? = null // 퍼센테이지 직접 설정

    private val progressPath = Path()
    private var progressPathMeasure: PathMeasure? = null
    private var progressPathLength = 0f

    init {
        progressAutoAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 3000
            interpolator = LinearInterpolator()

            addUpdateListener { animator ->
                LogUtil.d_dev("update percentage: ${animator.animatedValue}")
                percentage = animator.animatedValue as Float
                invalidate()
//                if(percentage == 100f) {
//                    percentage = 0f
//                    invalidate()
//                }

                setIconAndBgPaint(percentage)
            }
        }

        progressAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 50
            interpolator = LinearInterpolator()

            addUpdateListener { animator ->
                LogUtil.d_dev("update percentage: ${animator.animatedValue}")
                percentage = animator.animatedValue as Float
                invalidate()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtil.d_dev("onSizeChanged() w:${w}, h:${h}, oldw:${oldw}, oldh:${oldh}")

        iconRectPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = ViewSizeUtil.dpToPx(context, 1f).toFloat()
            color = iconRectColor
        }

        progressBgRectPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = ViewSizeUtil.dpToPx(context, 2f).toFloat()
            color = progressBgRectColor
        }

        progressRectPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = ViewSizeUtil.dpToPx(context, 2f).toFloat()
            color = progressRectColor
        }

        centerX = w / 2f
        centerY = h / 2f

        // 아이콘의 위치 계산
        val iconLeft = (width - iconSize) / 2 // ex) (100 - 10) / 2 = 45
        val iconTop = (height - iconSize) / 2

        iconRectF.set(
            (iconLeft - iconRectPadding).toFloat(),
            (iconTop - iconRectPadding).toFloat(),
            (iconLeft + iconSize + iconRectPadding).toFloat(),
            (iconTop + iconSize + iconRectPadding).toFloat()
        )

        progressBgRectF.set(
            (iconLeft - progressBgRectPadding).toFloat(),
            (iconTop - progressBgRectPadding).toFloat(),
            (iconLeft + iconSize + progressBgRectPadding).toFloat(),
            (iconTop + iconSize + progressBgRectPadding).toFloat()
        )

        progressRectF.set(
            (iconLeft - progressRectPadding).toFloat(),
            (iconTop - progressRectPadding).toFloat(),
            (iconLeft + iconSize + progressRectPadding).toFloat(),
            (iconTop + iconSize + progressRectPadding).toFloat()
        )

        val progressCornerRadius = ViewSizeUtil.dpToPx(context, 12f).toFloat()

        // Progress Path 초기화 - top 중앙에서 시작
        progressPath.reset()
        progressPath.moveTo(progressRectF.centerX(), progressRectF.top)  // 시작점을 top 중앙으로 설정

        // 우상단 모서리
        progressPath.lineTo(progressRectF.right - progressCornerRadius, progressRectF.top)
        progressPath.arcTo(
            RectF(
                progressRectF.right - progressCornerRadius * 2,
                progressRectF.top,
                progressRectF.right,
                progressRectF.top + progressCornerRadius * 2
            ),
            270f,
            90f,
            false
        )

        // 우하단 모서리
        progressPath.lineTo(progressRectF.right, progressRectF.bottom - progressCornerRadius)
        progressPath.arcTo(
            RectF(
                progressRectF.right - progressCornerRadius * 2,
                progressRectF.bottom - progressCornerRadius * 2,
                progressRectF.right,
                progressRectF.bottom
            ),
            0f,
            90f,
            false
        )

        // 좌하단 모서리
        progressPath.lineTo(progressRectF.left + progressCornerRadius, progressRectF.bottom)
        progressPath.arcTo(
            RectF(
                progressRectF.left,
                progressRectF.bottom - progressCornerRadius * 2,
                progressRectF.left + progressCornerRadius * 2,
                progressRectF.bottom
            ),
            90f,
            90f,
            false
        )

        // 좌상단 모서리
        progressPath.lineTo(progressRectF.left, progressRectF.top + progressCornerRadius)
        progressPath.arcTo(
            RectF(
                progressRectF.left,
                progressRectF.top,
                progressRectF.left + progressCornerRadius * 2,
                progressRectF.top + progressCornerRadius * 2
            ),
            180f,
            90f,
            false
        )

        // 다시 시작점으로
        progressPath.lineTo(progressRectF.centerX(), progressRectF.top)

        // PathMeasure 설정
        progressPathMeasure = PathMeasure(progressPath, false)
        progressPathLength = progressPathMeasure?.length ?: 0f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(
            iconRectF,
            ViewSizeUtil.dpToPx(context, 10f).toFloat(),
            ViewSizeUtil.dpToPx(context, 10f).toFloat(),
            iconRectPaint
        )

        val left = (width - iconSize) / 2
        val top = (height - iconSize) / 2
        iconDrawable.setBounds(
            left.toInt(),
            top.toInt(),
            (left + iconSize).toInt(),
            (top + iconSize).toInt()
        )
        iconDrawable.draw(canvas)


        if (percentage.toInt() != 0) {
            canvas.drawRoundRect(
                progressBgRectF,
                ViewSizeUtil.dpToPx(context, 12f).toFloat(),
                ViewSizeUtil.dpToPx(context, 12f).toFloat(),
                progressBgRectPaint
            )

            // Progress 그리기
            val pathEffect = DashPathEffect(
                floatArrayOf(progressPathLength, progressPathLength),
                progressPathLength - (progressPathLength * (percentage / 100f))
            )
            progressRectPaint.pathEffect = pathEffect
            canvas.drawPath(progressPath, progressRectPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 아이콘 크기 + (패딩 * 2) + 패딩으로 전체 크기 계산
        val desiredSize =
            iconSize + (iconRectPadding * 2) + ViewSizeUtil.dpToPx(context, 10f).toInt()

        setMeasuredDimension(
            desiredSize.toInt(),
            desiredSize.toInt()
        )
    }

    /**
     * 선택되었을 때와 아닐 때 아이콘 설정
     *
     * @param unselected
     * @param selected
     */
    fun setIcon(unselected: Int, selected: Int) {
        iconDrawable = ContextCompat.getDrawable(context, unselected)!!
        unselectedIcon = ContextCompat.getDrawable(context, unselected)!!
        selectedIcon = ContextCompat.getDrawable(context, selected)!!
        invalidate()
    }

    /**
     * 아이콘 크기 설정
     *
     * @param sizeDp
     */
    fun setIconSize(sizeDp: Float) {
        iconSize = ViewSizeUtil.dpToPx(context, sizeDp).toInt()
        invalidate()
    }

    /**
     * 아이콘 감싸는 네모 색 설정
     *
     * @param colorDp
     */
    fun setIconRectColor(colorDp: Int) {
        iconRectColor = ContextCompat.getColor(context, colorDp)
        setIconAndBgPaint(percentage)
        invalidate()
    }

    /**
     * 프로그래스 백그라운드 색과 게이지 색
     *
     * @param progressBgRectColor
     * @param progressRectColor
     */
    fun setProgressColor(proBgRectColor: Int, proRectColor: Int) {
        progressBgRectColor = ContextCompat.getColor(context, proBgRectColor)
        progressRectColor = ContextCompat.getColor(context, proRectColor)

        progressBgRectPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = ViewSizeUtil.dpToPx(context, 2f).toFloat()
            color = progressBgRectColor
        }

        progressRectPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = ViewSizeUtil.dpToPx(context, 2f).toFloat()
            color = progressRectColor
        }

        invalidate()
    }

    /**
     * 아이콘, 백그라운드 Paint 기본값으로 초기화
     */
    fun setInitState() {
        percentage = 0f
        setIconAndBgPaint(percentage)

        invalidate()
    }

    /**
     * 퍼센테이지 값으로 아이콘, 백그라운드 Paint 결정
     *
     * @param percent
     */
    private fun setIconAndBgPaint(percent: Float) {
        // 아이콘 감싸는 Rect paint 변경
        if (percent == 0f) {
            iconDrawable = unselectedIcon!!
            iconRectPaint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                strokeWidth = ViewSizeUtil.dpToPx(context, 1f).toFloat()
                color = iconRectColor
            }
        } else {
            iconDrawable = selectedIcon!!
            iconRectPaint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.FILL
                strokeCap = Paint.Cap.ROUND
                strokeWidth = ViewSizeUtil.dpToPx(context, 1f).toFloat()
                color = iconRectColor
            }
        }
    }

    fun setAutoPercentage() {
        progressAutoAnimator?.cancel()  // 기존 애니메이션 취소
        progressAutoAnimator?.setFloatValues(0f, 100.toFloat())  // 현재 값에서 새로운 값으로 애니메이션
        progressAutoAnimator?.start()
    }

    fun setPercentage(newPercentage: Int) {
        setIconAndBgPaint(newPercentage.toFloat())

        progressAnimator?.cancel()  // 기존 애니메이션 취소
        progressAnimator?.setFloatValues(percentage, newPercentage.toFloat())  // 현재 값에서 새로운 값으로 애니메이션
        progressAnimator?.start()
    }

    fun getPercentage(): Float = percentage
}