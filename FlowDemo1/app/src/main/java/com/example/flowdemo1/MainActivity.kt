package com.example.flowdemo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withStarted
import com.example.flowdemo1.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    private lateinit var job: Job
    private var jobList: ArrayList<Job> = ArrayList()
    private var str = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.btn1.setOnClickListener {
            startActivity(Intent(binding.root.context, MainActivity2::class.java))
        }

        mainViewModel.mLiveData1.observe(this as LifecycleOwner, Observer {
            Log.d("MYTAG", "LifeData1: ${it}")
        })


        // 1. StateFlow / launch + repeatOnLifecycle
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mStateFlow1
                    .collect {
                    if(it == "init") {
                        Log.d("MYTAG", "1. StateFlow1 / launch + repeatOnLifecycle: ${it}")
                    } else {
                        Log.d("MYTAG", "1. StateFlow1 / launch + repeatOnLifecycle: ${it}")
//                        for (i in 0..10) {
//                            Log.d("MYTAG", "1. StateFlow1 / launch + repeatOnLifecycle: ${i}")
//                            delay(1000L)
//                        }
                    }

                }
            }
        }

        // 2. StateFlow / launch
        lifecycleScope.launch {
            mainViewModel.mStateFlow2.collect {
                if(it == "init") {
                    Log.d("MYTAG", "2. StateFlow2 / launch: ${it}")
                } else {
                    Log.d("MYTAG", "2. StateFlow2 / launch: ${it}")
//                    for (i in 0..10) {
//                        Log.d("MYTAG", "2. StateFlow2 / launch: ${i}")
//                        delay(1000L)
//                    }
                }
            }
        }

        // 3. StateFlow / launchWhenStarted
        lifecycleScope.launchWhenStarted {
            mainViewModel.mStateFlow2.collect {
                if(it == "init") {
                    Log.d("MYTAG", "3. StateFlow2 / launchWhenStarted: ${it}")
                } else {
                    Log.d("MYTAG", "3. StateFlow2 / launchWhenStarted: ${it}")
//                    for (i in 0..10) {
//                        Log.d("MYTAG", "3. StateFlow2 / launchWhenStarted: ${i}")
//                        delay(1000L)
//                    }
                }
            }
        }

        // 4. StateFlow / launch + withStarted + launch
        lifecycleScope.launch {
            withStarted {
                lifecycleScope.launch {
                    mainViewModel.mStateFlow2.collect {
                        if(it == "init") {
                            Log.d("MYTAG", "4. StateFlow2 / launch + withStarted + launch: ${it}")
                        } else {
                            Log.d("MYTAG", "4. StateFlow2 / launch + withStarted + launch: ${it}")
//                            for (i in 0..10) {
//                                Log.d("MYTAG", "4. StateFlow2 / launch + withStarted + launch: ${i}")
//                                delay(1000L)
//                            }
                        }
                    }
                }
            }
        }


        // 5. SharedFlow / launch + repeatOnLifecycle
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mSharedFlow1.collect {
                    Log.d("MYTAG", "5. SharedFlow1 / launch + repeatOnLifecycle: ${it}")
//                    for (i in 0..10) {
//                        Log.d("MYTAG", "5. SharedFlow1 / launch + repeatOnLifecycle: ${i}")
//                        delay(1000L)
//                    }
                }
            }
        }

        // 6. SharedFlow / launch + repeatOnLifecycle
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mSharedFlow1.collect {
                    Log.d("MYTAG", "6. SharedFlow1 / launch + repeatOnLifecycle: ${it}")
//                    for (i in 0..10) {
//                        Log.d("MYTAG", "6. SharedFlow1 / launch + repeatOnLifecycle: ${i}")
//                        delay(1000L)
//                    }
                }
            }
        }

        // 7. SharedFlow / launchWhenStarted
        lifecycleScope.launchWhenStarted {
            mainViewModel.mSharedFlow2.collect {
                Log.d("MYTAG", "7. SharedFlow2 / launchWhenStarted: ${it}")
//                for(i in 0..10) {
//                    Log.d("MYTAG", "7. SharedFlow2 / launchWhenStarted: ${i}")
//                    delay(1000L)
//                }
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.numberStateFlow
                .collect {
                    Log.d("MYTAG", "numberStateFlow : ${it}")
                    binding.tvText.text = it.toString() + "!!"
                }
        }


        lifecycleScope.launch {
            delay(3000L)
            mainViewModel.mLiveData1.postValue("Emit")
            mainViewModel.mStateFlow1.emit("Emit")
            mainViewModel.mStateFlow2.emit("Emit")
//            delay(3000L)
//            mainViewModel.mSharedFlow1.emit("Emit")
//            mainViewModel.mSharedFlow2.emit("Emit")
        }

        binding.tvText.setOnClickListener {
            mainViewModel.plusNumber()
            lifecycleScope.launch {
//                mainViewModel.mLiveData1.postValue("Clicked Emit")
//                mainViewModel.mStateFlow1.emit("Clicked Emit")
//                mainViewModel.mStateFlow2.emit("Clicked Emit")
//                mainViewModel.mSharedFlow1.emit("Clicked Emit")
//                mainViewModel.mSharedFlow2.emit("Clicked Emit")
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        startJob()
//        Log.d("MYTAG", "onResume() StateFlow2 Job: ${job.isActive}")
    }

    override fun onStop() {
        super.onStop()
//        cancelJob()
//        Log.d("MYTAG", "onStop() StateFlow2 Job: ${job.isActive}")
    }

    private fun startJob() {
        for (i in jobList) {
            i.start()
        }
    }

    private fun cancelJob() {
        for (i in jobList) {
            i.cancel()
        }
    }
}