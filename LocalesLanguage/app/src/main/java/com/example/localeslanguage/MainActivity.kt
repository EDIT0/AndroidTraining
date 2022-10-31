package com.example.localeslanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localeslanguage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvWelcome.setOnClickListener {
            binding.tvWelcome.text = if(binding.tvWelcome.text == getString(R.string.welcome)) {
                getString(R.string.bye)
            } else {
                getString(R.string.welcome)
            }
        }
    }
}