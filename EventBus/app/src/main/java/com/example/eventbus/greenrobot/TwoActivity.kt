package com.example.eventbus.greenrobot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.eventbus.R

class TwoActivity : AppCompatActivity() {

    private val twoViewModel = TwoViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        findViewById<Button>(R.id.btnCreateEvent).setOnClickListener {
            twoViewModel.produceEvent(GreenRobotEventBus.getGreenRobotObject)
        }
    }
}