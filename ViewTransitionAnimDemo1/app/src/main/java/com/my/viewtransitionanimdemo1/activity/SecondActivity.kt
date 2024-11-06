package com.my.viewtransitionanimdemo1.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.my.viewtransitionanimdemo1.R
import com.my.viewtransitionanimdemo1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("MYTAG", "From FirstActivity, ${intent.getStringExtra("key1")} / ${intent.getIntExtra("key2", 0)}")

        backPressed()

    }

    private fun backPressed() {
        val intent = Intent()
        intent.putExtra("Data", "KO, US, UK")
        setResult(155, intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
                backPressedInvoke()
            }
        } else {
            backPressCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressedInvoke()
                }
            }
            onBackPressedDispatcher.addCallback(this, backPressCallback!!)
        }
    }

    private fun backPressedInvoke() {
        finishAfterTransition()
    }

    override fun onDestroy() {
        backPressCallback?.remove()
        super.onDestroy()
    }
}