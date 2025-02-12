package com.example.calendarexample1.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan


/**
 * Span to draw a dot centered under a section of text
 */
class CustomSelectSpan : LineBackgroundSpan {
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
        canvas.drawCircle((left + right) / 2f, (top + bottom) / 2f, radius, paint)
        paint.setColor(oldColor)
    }

    companion object {
        /**
         * Default radius used
         */
        const val DEFAULT_RADIUS = 3f
    }
}