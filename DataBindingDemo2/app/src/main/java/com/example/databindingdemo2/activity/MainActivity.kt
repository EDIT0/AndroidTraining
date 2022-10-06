package com.example.databindingdemo2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databindingdemo2.util.ListenerEvent
import com.example.databindingdemo2.R
import com.example.databindingdemo2.adapter.TextAdapter
import com.example.databindingdemo2.databinding.ActivityMainBinding
import com.example.databindingdemo2.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // 화면에 따른 뷰모델팩토리
    @Inject
    lateinit var inputEditTextViewModelFactory: InputEditTextViewModelFactory
    @Inject
    lateinit var textRecyclerViewModelFactory: TextRecyclerViewModelFactory
    @Inject
    lateinit var buttonViewModelFactory: ButtonViewModelFactory

    // 화면에 따른 뷰모델
    private lateinit var inputEditTextViewModel: InputEditTextViewModel
    private lateinit var textRecyclerViewModel: TextRecyclerViewModel
    private lateinit var buttonViewModel: ButtonViewModel
    private lateinit var listenerEvent: ListenerEvent

    // 리사이클러뷰 어댑터
    @Inject
    lateinit var textAdapter: TextAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        inputEditTextViewModel = ViewModelProvider(this, inputEditTextViewModelFactory).get(InputEditTextViewModel::class.java)
        textRecyclerViewModel = ViewModelProvider(this, textRecyclerViewModelFactory).get(TextRecyclerViewModel::class.java)
        buttonViewModel = ViewModelProvider(this, buttonViewModelFactory).get(ButtonViewModel::class.java)
        listenerEvent = ListenerEvent(this)

        binding.inputEditTextViewModel = inputEditTextViewModel
        binding.textRecyclerViewModel = textRecyclerViewModel
        binding.buttonViewModel = buttonViewModel
        binding.listenerEvent = listenerEvent
        binding.lifecycleOwner = this

        binding.includeRvText.rv.apply {
            adapter = textAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        textAdapter.setOnItemClickListener { adapterPosition, model ->
            Toast.makeText(this, "${adapterPosition} ${model.word}", Toast.LENGTH_SHORT).show()
        }

        Log.i("MYTAG", "Main ${inputEditTextViewModel.hashCode()} / ${inputEditTextViewModelFactory.hashCode()} / ${textAdapter.hashCode()}")
    }
}