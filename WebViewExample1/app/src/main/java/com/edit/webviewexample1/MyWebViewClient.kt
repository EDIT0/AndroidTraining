package com.edit.webviewexample1

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient: WebViewClient() {

    private lateinit var myWebViewClientCallback : MyWebViewClientCallback

    fun setMyWebViewClientCallback(listener: MyWebViewClientCallback) {
        myWebViewClientCallback = listener
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        myWebViewClientCallback.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        myWebViewClientCallback.onPageFinished(view, url)
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        myWebViewClientCallback.onReceivedError(view, request, error)
    }
}