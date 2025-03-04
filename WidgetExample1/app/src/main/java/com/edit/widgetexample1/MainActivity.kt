package com.edit.widgetexample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fromWidgetToMain = intent.extras?.getString("ACTION", null)
        if(fromWidgetToMain != null) {
            findViewById<TextView>(R.id.tvText).text = fromWidgetToMain
        }

        findViewById<TextView>(R.id.tvText).setOnClickListener {
            findViewById<TextView>(R.id.tvText).text = "Init"
        }
    }
}