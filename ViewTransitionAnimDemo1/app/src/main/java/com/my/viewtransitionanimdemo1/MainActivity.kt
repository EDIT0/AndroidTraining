package com.my.viewtransitionanimdemo1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.my.viewtransitionanimdemo1.activity.FirstActivity
import com.my.viewtransitionanimdemo1.activity.FragmentActivity
import com.my.viewtransitionanimdemo1.activity.SecondActivity
import com.my.viewtransitionanimdemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val launchActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

    }

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

        binding.btnActivityToActivity.setOnClickListener {
            launchActivity.launch(Intent(this@MainActivity, FirstActivity::class.java))
        }

        binding.btnFragmentToFragment.setOnClickListener {
            launchActivity.launch(Intent(this@MainActivity, FragmentActivity::class.java))
        }
    }
}