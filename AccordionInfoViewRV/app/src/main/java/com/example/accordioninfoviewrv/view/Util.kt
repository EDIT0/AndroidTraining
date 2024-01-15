package com.example.accordioninfoviewrv.view

import android.content.Context
import android.util.TypedValue

object Util {

    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

}