package com.example.databindingdemo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databindingdemo2.databinding.ActivitySecondBinding
import com.example.databindingdemo2.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    // 화면에 따른 뷰모델팩토리
    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    @Inject
    lateinit var textRecyclerViewModelFactory: TextRecyclerViewModelFactory
    @Inject
    lateinit var buttonViewModelFactory: ButtonViewModelFactory

    // 화면에 따른 뷰모델
    private lateinit var mainViewModel: MainViewModel
    private lateinit var textRecyclerViewModel: TextRecyclerViewModel
    private lateinit var buttonViewModel: ButtonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        textRecyclerViewModel = ViewModelProvider(this, textRecyclerViewModelFactory).get(TextRecyclerViewModel::class.java)
        buttonViewModel = ViewModelProvider(this, buttonViewModelFactory).get(ButtonViewModel::class.java)

        Log.i("MYTAG", "Second ${mainViewModel.hashCode()} / ${mainViewModelFactory.hashCode()}")

        binding.mainViewModelInSecondActivity = mainViewModel
        binding.textRecyclerViewModelInSecondActivity = textRecyclerViewModel
        binding.lifecycleOwner = this

        binding.includeRvText.textRecyclerViewModelInRecyclerText = textRecyclerViewModel
        binding.includeRvText.lifecycleOwner = this

        binding.includeRvText.rv.apply {
            adapter = TextAdapter()
            layoutManager = LinearLayoutManager(this@SecondActivity)
        }
    }
}