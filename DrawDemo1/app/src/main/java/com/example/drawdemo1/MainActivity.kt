package com.example.drawdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.drawdemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonClickListener()
    }

    fun buttonClickListener() {
        binding.imageDrawEraser.setOnClickListener {
            binding.drawView.toggleEraser()
            binding.imageDrawEraser.isSelected = binding.drawView.isEraserOn
        }
        binding.imageDrawUndo.setOnClickListener {
            binding.drawView.undo()
        }
        binding.imageDrawRedo.setOnClickListener {
            binding.drawView.redo()
        }
    }
}