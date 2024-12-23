package com.example.coroutinestudy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestudy.databinding.ActivityCoroutineErrorBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CoroutineErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutineErrorBinding

    val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        LogUtil.e_dev("루트에서 에러 처리 ${coroutineContext[CoroutineName.Key]?.name} / ${throwable}")
    }
    var job1: Job? = null
    var job2: Job? = null
    var job3: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCoroutineErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            repeat(times = 3, action = { i ->
                delay(2000L)
                call1()
            })
        }
    }

    fun call1() {
        job1?.cancel()
        job1 = CoroutineScope(errorHandler + CoroutineName("Job1"))
            .launch(Dispatchers.IO) {
                request1()
                request2()
                request3()
            }
        job1?.invokeOnCompletion { throwable ->
            LogUtil.e_dev("Job1에서 에러처리 ${throwable}")
        }
    }

    fun call2() {
        job2?.cancel()
        job2 = CoroutineScope(errorHandler + CoroutineName("Job2"))
            .launch(Dispatchers.IO) {
                request1()
                request2()
            }
    }

    fun call3() {
        job3?.cancel()
        job3 = CoroutineScope(errorHandler + CoroutineName("Job3"))
            .launch(Dispatchers.IO) {
                request1()
            }
    }

    suspend fun request1() = coroutineScope {
        val response = flow<Int> {
            emit(1)
            delay(3000L)
            emit(2)
            delay(3000L)
            emit(3)
        }.map {
            LogUtil.d_dev("request1 ${it}")
            it + 100
        }.filter {
            true
        }.catch {
            LogUtil.e_dev("request1 catch ${it}")
        }.collect {
            LogUtil.d_dev("${coroutineContext[CoroutineName.Key]?.name} / ${it}")
        }
    }

    suspend fun request2() = coroutineScope {
        val response = flow<Int> {
            emit(1)
            delay(3000L)
            emit(2)
            delay(3000L)
            emit(3)
        }.map {
            LogUtil.d_dev("request2 ${it}")
            it + 100
        }.filter {
            if(it > 101) {
                error("request2 Error!!!")
            } else {
                true
            }
        }.catch {
            LogUtil.e_dev("request2 catch ${it}")
            /**
             * 루트로 에러 던져서 처리 가능.
             * 다만, 루트(부모) 코루틴이 취소
             * 루트(부모) 코루틴이 취소되는 것을 방지하려면 에러 던지지 말고, .catch 안에서 처리하여야 함.
             */
//            throw Exception(it)
        }.collect {
            LogUtil.d_dev("${coroutineContext[CoroutineName.Key]?.name} / ${it}")
        }
    }

    suspend fun request3() = coroutineScope {
        val response = flow<Int> {
            emit(1)
            delay(3000L)
            emit(2)
            delay(3000L)
            emit(3)
        }.map {
            LogUtil.d_dev("request3 ${it}")
            it + 100
        }.filter {
            if(it > 101) {
                error("request3 Error!!!")
            } else {
                true
            }
        }.catch {
            LogUtil.e_dev("request3 catch ${it}")
        }.collect {
            LogUtil.d_dev("${coroutineContext[CoroutineName.Key]?.name} / ${it}")
        }
    }

    override fun onStop() {
        super.onStop()
        job1?.cancel()
        job2?.cancel()
        job3?.cancel()
    }
}