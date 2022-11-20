package com.example.eventbus.otto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.eventbus.R

class SecondActivity : AppCompatActivity() {

    private val secondViewModel = SecondViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.btnCreateEvent).setOnClickListener {
            secondViewModel.produceEvent(OttoEventBus.getOttoObject)
        }
    }
}