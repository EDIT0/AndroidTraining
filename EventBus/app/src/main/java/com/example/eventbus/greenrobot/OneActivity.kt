package com.example.eventbus.greenrobot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.eventbus.R
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class OneActivity : AppCompatActivity() {

    private val oneViewModel = OneViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        GreenRobotEventBus.registerBus(this, GreenRobotEventBus.getGreenRobotObject)

        buttonClickListener()
        observerViewModel()
    }

    fun buttonClickListener() {
        // Move to SecondActivity.class
        findViewById<TextView>(R.id.tvMoveToTwo).setOnClickListener {
            startActivity(Intent(this, TwoActivity::class.java))
        }

        // Move to Thirdctivity.class
        findViewById<TextView>(R.id.tvMoveToThree).setOnClickListener {
            startActivity(Intent(this, ThreeActivity::class.java))
        }
    }

    fun observerViewModel() {
        oneViewModel.str.observe(this) {
            Log.i("MYTAG", "OneActivity Thread Name: ${Thread.currentThread().name}")
            findViewById<TextView>(R.id.tvCount).text = "${it}"
            Log.i("MYTAG", "OneActivity 관찰자 ${it}")
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun receiveEventBus(data: String) {
        Log.i("MYTAG", "OneActivity-receiveEventBus ${data}")
        oneViewModel.setData(data)
    }

    override fun onDestroy() {
        GreenRobotEventBus.unregisterBus(this, GreenRobotEventBus.getGreenRobotObject)
        super.onDestroy()
    }
}