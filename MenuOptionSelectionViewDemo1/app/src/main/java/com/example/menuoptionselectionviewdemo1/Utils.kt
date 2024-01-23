package com.example.menuoptionselectionviewdemo1

import android.content.Context
import android.widget.Toast
import java.text.NumberFormat
import java.util.Locale

object Utils {

    private var toast: Toast? = null
    fun showToast(context: Context, message: String?) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun formatCurrency(amount: Long): String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
        return formatter.format(amount)
    }

}