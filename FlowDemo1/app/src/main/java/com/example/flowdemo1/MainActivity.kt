package com.example.flowdemo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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

        mainViewModel = MainViewModel()

        binding.btn1.setOnClickListener {
            startActivity(Intent(binding.root.context, MainActivity2::class.java))
        }

        mainViewModel.mLiveData1.observe(this as LifecycleOwner, Observer {
            Log.d("MYTAG", "LifeData1: ${it}")
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mStateFlow1
                    .collect {
                    if(it == "init") {
                        Log.d("MYTAG", "StateFlow1: ${it}")
                    } else {
                        Log.d("MYTAG", "StateFlow1: ${it}")
//                        for (i in 0..10) {
//                            Log.d("MYTAG", "StateFlow1: ${i}")
//                            delay(1000L)
//                        }
                    }

                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.mStateFlow2.collect {
                if(it == "init") {
                    Log.d("MYTAG", "launch StateFlow2: ${it}")
                } else {
                    Log.d("MYTAG", "launch StateFlow2: ${it}")
                    for (i in 0..10) {
                        Log.d("MYTAG", "launch StateFlow2: ${i}")
                        delay(1000L)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.mStateFlow2.collect {
                if(it == "init") {
                    Log.d("MYTAG", "launchWhenStarted StateFlow2: ${it}")
                } else {
                    Log.d("MYTAG", "launchWhenStarted StateFlow2: ${it}")
                    for (i in 0..10) {
                        Log.d("MYTAG", "launchWhenStarted StateFlow2: ${i}")
                        delay(1000L)
                    }
                }
            }
        }

        lifecycleScope.launch {
            withStarted {
                lifecycleScope.launch {
                    mainViewModel.mStateFlow2.collect {
                        if(it == "init") {
                            Log.d("MYTAG", "withStarted StateFlow2: ${it}")
                        } else {
                            Log.d("MYTAG", "withStarted StateFlow2: ${it}")
                            for (i in 0..10) {
                                Log.d("MYTAG", "withStarted StateFlow2: ${i}")
                                delay(1000L)
                            }
                        }
                    }
                }
            }
        }



//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                mSharedFlow1.collect {
//                    Log.d("MYTAG", "SharedFlow1: ${it}")
//                    for (i in 0..10) {
//                        Log.d("MYTAG", "SharedFlow1: ${i}")
//                        delay(1000L)
//                    }
//                }
//            }
//        }
//
//        lifecycleScope.launchWhenStarted {
//            mSharedFlow2.collect {
//                Log.d("MYTAG", "SharedFlow2: ${it}")
//                for(i in 0..10) {
//                    Log.d("MYTAG", "SharedFlow2: ${i}")
//                    delay(1000L)
//                }
//            }
//        }


        lifecycleScope.launch {
            delay(3000L)
            mainViewModel.mLiveData1.postValue("Emit")
            mainViewModel.mStateFlow1.emit("Emit")
            mainViewModel.mStateFlow2.emit("Emit")
            delay(3000L)
            mainViewModel.mSharedFlow1.emit("Emit")
            mainViewModel.mSharedFlow2.emit("Emit")
        }

        binding.tvText.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.mLiveData1.postValue("Clicked Emit")
                mainViewModel.mStateFlow1.emit("Clicked Emit")
                mainViewModel.mStateFlow2.emit("Clicked Emit")
                mainViewModel.mSharedFlow1.emit("Clicked Emit")
                mainViewModel.mSharedFlow2.emit("Clicked Emit")
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