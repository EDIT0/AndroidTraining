package com.edit.customsnackbarexample1

import android.app.Application
import android.os.Looper
import android.view.View
import java.util.logging.Handler

class App: Application() {
    override fun onCreate() {
        super.onCreate()

    }

    fun showCustomTopSnackBar(view: View, message: String, time: Long = 0L) {
        android.os.Handler(Looper.getMainLooper())
            .postDelayed(
                Runnable {
                    CustomTopSnackBar(view, message, time).show()
                }, 500L)
    }

    fun showCustomBottomSnackBar(view: View, message: String, time: Long = 0L) {
        android.os.Handler(Looper.getMainLooper())
            .postDelayed(
                Runnable {
                    CustomBottomSnackBar(view, message, time).show()
                }, 500L)
    }
}