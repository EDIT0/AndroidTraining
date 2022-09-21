package com.example.databindingdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.example.databindingdemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var activityMainBidning: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBidning = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // 뷰모델의 생명주기를 현재 액티비티로 지정
        activityMainBidning.lifecycleOwner = this

        val vm = MainViewModel()
        activityMainBidning.vm = vm

        vm.getData()

    }
}