package com.edit.customsnackbarexample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edit.customsnackbarexample1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        App().showCustomTopSnackBar(binding.root, "두번째 액티비티로 들어오신 것을 환영합니다.", 3000L)

        val str = "깜짝 할인 진행 중!!!\n최대 60% 할인된 가격으로 지금 바로 만나보세요."

        binding.btnTopSnackBar.setOnClickListener {
            App().showCustomTopSnackBar(binding.root, str, 2000L)
        }

        binding.btnBottomSnackBar.setOnClickListener {
            App().showCustomBottomSnackBar(binding.root, str, 0L)
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }
}