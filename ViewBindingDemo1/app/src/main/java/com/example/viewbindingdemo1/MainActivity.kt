package com.example.viewbindingdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewbindingdemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 바인딩 객체로 뷰에 바로 접근이 가능하다.
        activityMainBinding.txtHello.text = "Hello ViewBinding"

        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.frameLayout, OneFragment())
            .commitAllowingStateLoss()
    }
}