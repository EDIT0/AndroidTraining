package com.edit.webviewexample1

import android.os.Message
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

class MyWebViewChromeClient: WebChromeClient() {
    private lateinit var myWVChromeClientCallback : MyWebViewChromeClientCallback

    fun setMyWVChromeClientCallback(listener: MyWebViewChromeClientCallback) {
        myWVChromeClientCallback = listener
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        myWVChromeClientCallback.onJsAlert(view, url, message, result)
        return true
    }

    override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
        myWVChromeClientCallback.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        return false
    }
}