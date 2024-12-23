package com.hs.workation.core.util

import java.text.NumberFormat
import java.util.Locale

object NumberFormatter {

    fun Int.currencyFormatter(): String {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(this)
    }
}