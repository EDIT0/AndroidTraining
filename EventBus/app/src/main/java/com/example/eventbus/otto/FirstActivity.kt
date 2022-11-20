package com.example.eventbus.otto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.eventbus.R
import com.squareup.otto.Subscribe

class FirstActivity : AppCompatActivity() {

    val firstViewModel = FirstViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        // 이벤트 버스 구독
        OttoEventBus.registerBus(this, OttoEventBus.getOttoObject)

        buttonClickListener()
        observerViewModel()
    }

    fun buttonClickListener() {
        // Move to SecondActivity.class
        findViewById<TextView>(R.id.tvMoveToSecond).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        // Move to Thirdctivity.class
        findViewById<TextView>(R.id.tvMoveToThird).setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }

    fun observerViewModel() {
        firstViewModel.str.observe(this) {
            Log.i("MYTAG", "FirstActivity Thread Name: ${Thread.currentThread().name}")
            findViewById<TextView>(R.id.tvCount).text = "Count : ${it}"
            Log.i("MYTAG", "FirstActivity 관찰자 ${it}")
        }
    }

    @Subscribe
    fun receiveEventBus(data: String) {
        Log.i("MYTAG", "FirstActivity-receiveEventBus ${data}")
        firstViewModel.setData(data)
    }

    override fun onDestroy() {
        OttoEventBus.unregisterBus(this, OttoEventBus.getOttoObject)
        super.onDestroy()
    }
}