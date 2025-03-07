package com.edit.widgetexample1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.edit.widgetexample1.databinding.ActivityDeepLinkBinding
import com.edit.widgetexample1.model.UserInfo

class DeepLinkActivity : AppCompatActivity() {

    companion object {
        const val WIDGET_SCHEME = "myapp"
        const val WIDGET_HOST = "widget"
        const val PATH_URI_TYPE = "uri_type"
        const val PATH_INTENT_TYPE = "intent_type"
    }

    private lateinit var binding: ActivityDeepLinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeepLinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("MYTAG", "Intent action: " + intent?.action)
        Log.i("MYTAG", "Intent scheme, host: ${intent?.scheme}, ${intent.data?.host}")
        Log.i("MYTAG", "Intent uri path: " + intent?.data?.path)

        deepLinkProcess(intent)
    }

    private fun deepLinkProcess(intent: Intent?) {
        if(intent != null && intent.data != null) {
            val data = intent.data!!
            if(intent.scheme == WIDGET_SCHEME) {
                data.host?.let { host ->
                    if(host == WIDGET_HOST) {
                        intent.data?.path?.let { path ->
                            if(path == "/${PATH_URI_TYPE}") {
                                val itemId = data.getQueryParameter("item_id")
                                val itemBoolean = data.getQueryParameter("item_boolean")?.toBoolean() ?: false
                                val userName = data.getQueryParameter("item_user_name")
                                val userNumber = data.getQueryParameter("item_user_data")

                                Log.d("MYTAG", "Received itemId: $itemId")
                                Log.d("MYTAG", "Received itemBoolean: $itemBoolean")
                                Log.d("MYTAG", "Received userData: ${userName}, ${userNumber}")
                            } else if(path == "/${PATH_INTENT_TYPE}") {
                                val itemId = intent.getStringExtra("item_id")
                                val itemBoolean = intent.getBooleanExtra("item_boolean", false)

                                val userData: UserInfo? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    intent.getSerializableExtra("item_user_data", UserInfo::class.java)
                                } else {
                                    @Suppress("DEPRECATION")
                                    intent.getSerializableExtra("item_user_data") as? UserInfo
                                }

                                Log.d("MYTAG", "Received itemId: $itemId")
                                Log.d("MYTAG", "Received itemBoolean: $itemBoolean")
                                Log.d("MYTAG", "Received userData: ${userData?.name}, ${userData?.number}")
                            } else {

                            }
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("MYTAG", "onNewIntent action: " + intent?.action)
        Log.i("MYTAG", "onNewIntent scheme, host: ${intent?.scheme}, ${intent?.data?.host}")
        Log.i("MYTAG", "onNewIntent uri path: " + intent?.data?.path)

        deepLinkProcess(intent)
    }
}