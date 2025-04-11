package com.my.animdemo1

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.view.View
import android.view.animation.LinearInterpolator

object AnimUtil {

    private var alphaValueAnimator: ValueAnimator? = null

    fun alpha(view: View, duration: Long) {
        alphaValueAnimator = ValueAnimator.ofFloat(0f, 1.0f)
        alphaValueAnimator?.let { animator ->
            animator.interpolator = LinearInterpolator()
            animator.duration = duration
            animator.addUpdateListener {
                view.alpha = it.animatedValue as Float
            }
        }
        alphaValueAnimator?.start()
    }

    fun dropFromTop(view: View) {
        ValueAnimator.ofObject(PointFTypeEvaluator(), PointF(0f, -500f), PointF(0f, 0f)).apply {
            duration = 1000 // 애니메이션 지속 시간

            addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as PointF
                // TextView의 위치를 애니메이션 값에 따라 업데이트
                view.translationX = animatedValue.x
                view.translationY = animatedValue.y
            }

            start()
        }
    }

    class PointFTypeEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            val x = startValue.x + fraction * (endValue.x - startValue.x)
            val y = startValue.y + fraction * (endValue.y - startValue.y)
            return PointF(x, y)
        }
    }

}