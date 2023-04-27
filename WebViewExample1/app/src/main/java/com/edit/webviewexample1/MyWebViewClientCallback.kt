package com.edit.webviewexample1

import android.graphics.Bitmap
import android.webkit.WebView

interface MyWebViewClientCallback {
    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
    fun onPageFinished(view: WebView?, url: String?)
}