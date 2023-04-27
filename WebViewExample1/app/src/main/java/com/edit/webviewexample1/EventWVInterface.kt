package com.edit.webviewexample1

import android.util.Log
import android.webkit.JavascriptInterface

class EventWVInterface {

    companion object {
        const val EventWVInterface = "MyAppBridge"
    }

    private lateinit var eventWVInterfaceCallback: EventWVInterfaceCallback

    fun setEventWVInterfaceCallback(listener: EventWVInterfaceCallback) {
        eventWVInterfaceCallback = listener
    }

    @JavascriptInterface
    fun showToast(message: String) {
        Log.i("MYTAG", "showToast() ${message}")
        eventWVInterfaceCallback.showToast(message)
    }

    @JavascriptInterface
    fun backPressed() {
        Log.i("MYTAG", "backPressed() ")
        eventWVInterfaceCallback.backPressed()
    }

}