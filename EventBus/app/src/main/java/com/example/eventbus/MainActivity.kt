package com.example.eventbus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eventbus.greenrobot.OneActivity
import com.example.eventbus.otto.FirstActivity


class MainActivity : AppCompatActivity() {

    val mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonClickListener()
    }

    fun buttonClickListener() {
        findViewById<TextView>(R.id.btnOtto).setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }

        findViewById<TextView>(R.id.btnGreenRobot).setOnClickListener {
            startActivity(Intent(this, OneActivity::class.java))
        }
    }
}