package com.my.localstoragedemo1

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.my.localstoragedemo1.databinding.ActivityMainBinding
import com.my.localstoragedemo1.external.ExternalStorageActivity
import com.my.localstoragedemo1.internal.InternalStorageActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnInternalStorage.setOnClickListener {
            startActivity(Intent(this, InternalStorageActivity::class.java))
        }

        binding.btnExternalStorage.setOnClickListener {
            startActivity(Intent(this, ExternalStorageActivity::class.java))
        }
    }

    companion object {
        const val BASE_IMAGE_PATH = "/image"
        const val BASE_TEXT_PATH = "/text"

        const val BASE_EXTERNAL_COMMON_IMAGE_PATH = "/Pictures${BASE_IMAGE_PATH}" // 이미지 공용 외부 저장소 전용
        // Environment.DIRECTORY_DOCUMENTS 텍스트 공용 외부 저장소 전용
    }
}