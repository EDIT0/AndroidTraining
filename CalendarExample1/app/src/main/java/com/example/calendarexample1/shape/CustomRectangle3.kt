package com.example.calendarexample1.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.text.style.LineBackgroundSpan
import android.util.Log
import com.example.calendarexample1.MainActivity.Companion.TAG_C

/**
 * Span to draw a dot centered under a section of text
 */
class CustomRectangle3 : LineBackgroundSpan {
    private val radius: Float
    private val color: Int

    /**
     * Create a span to draw a dot using default radius and color
     *
     * @see .DotSpan
     * @see .DEFAULT_RADIUS
     */
    constructor() {
        radius = DEFAULT_RADIUS
        color = 0
    }

    /**
     * Create a span to draw a dot using a specified color
     *
     * @param color color of the dot
     * @see .DotSpan
     * @see .DEFAULT_RADIUS
     */
    constructor(color: Int) {
        radius = DEFAULT_RADIUS
        this.color = color
    }

    /**
     * Create a span to draw a dot using a specified radius
     *
     * @param radius radius for the dot
     * @see .DotSpan
     */
    constructor(radius: Float) {
        this.radius = radius
        color = 0
    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *
     * @param radius radius for the dot
     * @param color color of the dot
     */
    constructor(radius: Float, color: Int) {
        this.radius = radius
        this.color = color
    }

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        p7: CharSequence,
        start: Int,
        end: Int,
        lineNum: Int
    ) {
        val oldColor: Int = paint.getColor()
        if (color != 0) {
            paint.setColor(color)
        }
//        canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        var rect = RectF(left.toFloat() + 5, bottom.toFloat(), right.toFloat() - 5, bottom.toFloat() + 10)
        var rect2 = RectF(left.toFloat() + 5, bottom.toFloat() + 15, right.toFloat() - 5, bottom.toFloat() + 15 + 10)
        var rect3 = RectF(left.toFloat() + 5, bottom.toFloat() + 30, right.toFloat() - 5, bottom.toFloat() + 30 + 10)
//        val rect = RectF(0f,49f,162f,49f + 20)
//        val rect2 = RectF(0f, 74f, 162f, 74f + 20)
        val corners = floatArrayOf(
            10f, 10f,   // Top left radius in px
            10f, 10f,   // Top right radius in px
            10f, 10f,     // Bottom right radius in px
            10f, 10f      // Bottom left radius in px
        )

        Log.i(TAG_C, "left:${left}, top:${top}, right:${right}, bottom:${bottom}")
        val path = Path()
        path.addRoundRect(rect, corners, Path.Direction.CW)
        path.addRoundRect(rect2, corners, Path.Direction.CW)
        path.addRoundRect(rect3, corners, Path.Direction.CW)
        canvas.drawPath(path, paint)
        paint.setColor(oldColor)
    }

    companion object {
        /**
         * Default radius used
         */
        const val DEFAULT_RADIUS = 3f
    }
}