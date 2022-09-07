package com.example.sociallogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sociallogin.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnKakao.setOnClickListener {
            startActivity(Intent(this, KakaoLoginActivity::class.java))
        }

        binding.btnNaver.setOnClickListener {
            startActivity(Intent(this, NaverLoginActivity::class.java))
        }

        binding.btnGoogle.setOnClickListener {
            startActivity(Intent(this, GoogleLoginActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}