package com.edit.webviewexample1

import android.os.Message
import android.webkit.JsResult
import android.webkit.WebView

interface MyWebViewChromeClientCallback {
    fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean
    fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean
}