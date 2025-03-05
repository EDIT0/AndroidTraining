package com.edit.widgetexample1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.edit.widgetexample1.model.UserInfo

class DeepLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)

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
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("MYTAG", "${intent?.data}")
    }
}