package com.edit.webviewexample1

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.edit.webviewexample1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MyWebViewChromeClientCallback, MyWebViewClientCallback, EventWVInterfaceCallback {

    private lateinit var binding: ActivityMainBinding
    private val myWebViewChromeClient = MyWebViewChromeClient()
    private val myWebViewClient = MyWebViewClient()
    private val eventWVInterface = EventWVInterface()
    private val baseUrl = BuildConfig.BASE_URL + "web_event_js.php"
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        webViewSetting()
        buttonClickListener()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetting() {
        binding.wvEvent.apply {
            addJavascriptInterface(eventWVInterface, EventWVInterface.EventWVInterface)
            webViewClient = myWebViewClient
            webChromeClient = myWebViewChromeClient
//            webChromeClient = WebChromeClient()
        }

        binding.wvEvent.settings.apply {
            javaScriptEnabled = true // 웹뷰를 사용하면 네이티브 영역에서 코드로 처리 되는 것이 거의 없는 경우가 많습니다. 그래서 자바스크립트로 이루어져 있는 기능들을 사용하기 위하여 해당 속성을 추가해야합니다.
            Log.i("MYTAG","유저 정보: ${binding.wvEvent.settings.userAgentString}")
            javaScriptCanOpenWindowsAutomatically = true // 필요에 의해 팝업창을 띄울 경우가 있는데, 해당 속성을 추가해야 window.open() 이 제대로 작동합니다.
            loadsImagesAutomatically = true // 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정하는 속성입니다.
            useWideViewPort = true // 웹뷰가 wide viewport를 사용하도록 설정하는 속성입니다. 그래서 html 컨텐츠가 웹뷰에 맞게 나타나도록 합니다.
            setSupportZoom(false) // 확대 축소 기능을 사용할 수 있도록 설정하는 속성입니다.
//            LOAD_CACHE_ELSE_NETWORK : 기간이 만료돼 캐시를 사용할 수 없을 경우 네트워크를 사용합니다.
//            LOAD_CACHE_ONLY : 네트워크를 사용하지 않고 캐시를 불러옵니다.
//            LOAD_DEFAULT : 기본적인 모드로 캐시를 사용하고 만료된 경우 네트워크를 사용해 로드합니다.
//            LOAD_NORMAL : 기본적인 모드로 캐시를 사용합니다.
//            LOAD_NO_CACHE : 캐시모드를 사용하지 않고 네트워크를 통해서만 호출합니다.
            cacheMode = WebSettings.LOAD_NO_CACHE
            domStorageEnabled = true // 로컬 스토리지 사용 여부를 설정하는 속성으로 팝업창등을 '하루동안 보지 않기' 기능 사용에 필요합니다.
            allowFileAccess = false
            defaultTextEncodingName = "UTF-8"
            loadWithOverviewMode = true // 컨텐츠가 웹뷰보다 클때 스크린크기에 맞추기
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            WebView.setWebContentsDebuggingEnabled(true) // WebView에서 디버그 가능하도록 설정
            setSupportMultipleWindows(true) // WebView가 multiple 윈도를 지원하도록 설정
        }

        myWebViewChromeClient.setMyWVChromeClientCallback(this)
        myWebViewClient.setMyWebViewClientCallback(this)
        eventWVInterface.setEventWVInterfaceCallback(this)

        binding.wvEvent.apply {
//            setHorizontalScrollbarOverlay(false)
//            setVerticalScrollbarOverlay(true)
            setVerticalScrollBarEnabled(false)
            setHorizontalScrollBarEnabled(false)
            loadUrl(baseUrl)
        }
    }

    fun buttonClickListener() {
        binding.ibBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
        toast?.cancel()
        toast = Toast.makeText(binding.root.context, "onCreateWindow()", Toast.LENGTH_SHORT)
        toast?.show()
        return false
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        toast?.cancel()
        toast = Toast.makeText(binding.root.context, "onJsAlert()", Toast.LENGTH_SHORT)
        toast?.show()
        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        binding.loadingLayout.visibility = View.VISIBLE
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        binding.loadingLayout.visibility = View.GONE
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        Log.i("MYTAG", "Error Code: ${error?.errorCode} / ${error?.description}")
    }

    override fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun backPressed() {
        onBackPressed()
    }

    override fun onDestroy() {
        binding.wvEvent?.let {
            it.removeJavascriptInterface(EventWVInterface.EventWVInterface)
            it.clearCache(true)
            it.stopLoading()
            it.destroy()
        }
        super.onDestroy()
    }

    @Override
    override fun onBackPressed() {
        if(binding.wvEvent.canGoBack()) {
            binding.wvEvent.goBack()
        } else {
            super.onBackPressed()
        }
    }
}