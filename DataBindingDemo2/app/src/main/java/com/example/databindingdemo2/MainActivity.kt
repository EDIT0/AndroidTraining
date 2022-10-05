package com.example.databindingdemo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databindingdemo2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val mainViewModel = MainViewModel()
        val textRecyclerViewModel = TextRecyclerViewModel(application)
        binding.mainViewModelInMainActivity = mainViewModel
        binding.textRecyclerViewModelInMainActivity = textRecyclerViewModel
        binding.lifecycleOwner = this

        binding.includeRvText.mainViewModelInRecyclerText = mainViewModel
        binding.includeRvText.textRecyclerViewModelInRecyclerText = textRecyclerViewModel
    }
}