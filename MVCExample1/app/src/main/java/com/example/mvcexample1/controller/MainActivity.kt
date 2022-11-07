package com.example.mvcexample1.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.mvcexample1.R
import com.example.mvcexample1.databinding.ActivityMainBinding
import com.example.mvcexample1.model.Movie
import com.example.mvcexample1.network.ApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                Log.i(TAG, "${movie.searchMovies(binding.etInput.text.toString().trim())}")
            }
        }

    }
}