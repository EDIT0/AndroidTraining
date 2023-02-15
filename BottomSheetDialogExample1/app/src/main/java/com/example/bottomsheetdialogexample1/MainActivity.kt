package com.example.bottomsheetdialogexample1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomsheetdialogexample1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnPeekHeightDialog1.setOnClickListener {
            startActivity(Intent(binding.root.context, PeekHeightDialog1Activity::class.java))
        }

        binding.btnPeekHeightDialog2.setOnClickListener {
            startActivity(Intent(binding.root.context, PeekHeightDialog2Activity::class.java))
        }

        binding.btnBottomSheetFragment3.setOnClickListener {
            startActivity(Intent(binding.root.context, BottomSheetDialogActivity::class.java))
        }
    }
}