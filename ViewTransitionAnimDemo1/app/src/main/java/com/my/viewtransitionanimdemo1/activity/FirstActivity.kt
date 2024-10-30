package com.my.viewtransitionanimdemo1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.my.viewtransitionanimdemo1.R
import com.my.viewtransitionanimdemo1.databinding.ActivityFirstBinding
import com.my.viewtransitionanimdemo1.navigate.NavActivity
import com.my.viewtransitionanimdemo1.navigate.NavActivityImpl

class FirstActivity : AppCompatActivity() {

    private val launchActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("MYTAG", "launchActivity ${result.resultCode} / ${result.data}")
        Log.d("MYTAG", "${result.data?.getStringExtra("Data")}")
    }
    private val navActivity: NavActivity = NavActivityImpl()

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnOpenSecondActivity.setOnClickListener {
            val animList: List<androidx.core.util.Pair<View, String>> = listOf(
                androidx.core.util.Pair<View, String>(binding.btnOpenSecondActivity, "main"),
            )
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@FirstActivity, *animList.toTypedArray())
            val dataBundle = Bundle().apply {
                putString("key1", "value1")
                putInt("key2", 123)
            }
            navActivity.navigateToSecondActivity(this@FirstActivity, launchActivity, options, dataBundle)
        }


        binding.ivAndroid.setOnClickListener {
            val animList: List<androidx.core.util.Pair<View, String>> = listOf(
                androidx.core.util.Pair<View, String>(binding.ivAndroid, "ivAndroid"),
            )
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@FirstActivity, *animList.toTypedArray())
            navActivity.navigateToSecondActivity(this@FirstActivity, launchActivity, options, null)
        }
    }
}