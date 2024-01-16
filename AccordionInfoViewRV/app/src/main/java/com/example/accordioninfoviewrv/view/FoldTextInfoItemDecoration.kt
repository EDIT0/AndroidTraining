package com.example.accordioninfoviewrv.view

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class FoldTextInfoItemDecoration(
    private val widthMargin: Float, // 좌우 Margin
    private val height: Float, // 사각형의 height
    private val lineColor: Int
): RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()
        val paint = Paint().apply { color = lineColor }
        for(i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val top = view.bottom.toFloat() + (view.layoutParams as RecyclerView.LayoutParams).bottomMargin
            val bottom = top + height   // 세로 길이 = 5 (bottom - top = height)

            // 좌표 (left, top) / (right, bottom) 값을 대각선으로 가지는 사각형
            c.drawRect(left + widthMargin, top, right - widthMargin, bottom, paint)
        }
    }
}