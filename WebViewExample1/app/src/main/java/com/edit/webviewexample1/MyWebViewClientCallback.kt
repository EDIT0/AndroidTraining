package com.edit.webviewexample1

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView

interface MyWebViewClientCallback {
    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
    fun onPageFinished(view: WebView?, url: String?)
    fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?)
}