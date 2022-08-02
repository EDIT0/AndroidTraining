package com.example.coroutinestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestudy.databinding.ActivityCoroutineFlowBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class CoroutineFlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutineFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineFlowBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // simple1
//        lifecycleScope.launch {
//            launch {
//                for(k in 1..3) {
//                    Log.i("MYTAG", "I'm not blocked ${k}")
//                    delay(1000L)
//                }
//            }
//            simple1().collect {
//                Log.i("MYTAG", "${it}")
//            }
//        }

        // simple2
//        lifecycleScope.launch {
//            Log.i("MYTAG", "Calling simple function...")
//            val flow = simple2()
//            Log.i("MYTAG", "Calling collect...")
//            flow.collect {
//                Log.i("MYTAG", "1. ${it}")
//            }
//            Log.i("MYTAG", "Calling collect again...")
//            flow.collect {
//                Log.i("MYTAG", "2. ${it}")
//            }
//        }

        // simple3
        // withTimeoutOrNull은 실행 시간이 설정된 시간 초과 될 때 flow를 중지한다.
//        lifecycleScope.launch {
//            withTimeoutOrNull(2500L) { // Timeout after 2500ms
//                simple3().collect {
//                    Log.i("MYTAG", "${it}")
//                }
//            }
//            Log.i("MYTAG", "Done")
//        }

        // simple4
        // asFlow()로 쉽게 flow로 변경 가능
//        simple4()

        /**
         * 중간 연산자
         * */
        // 1. map
//        lifecycleScope.launch {
//            (1..3)
//                .asFlow()
//                .map {
//                    // (1~3까지의 값은 리턴 타입이 Int형 이였는데 performRequest()에서 String을 반환했다.
//                    performRequest(it)
//                }
//                .collect {
//                    // 소비자는 String 형태로 반환 받는다.
//                    Log.i("MYTAG", "${it}")
//                }
//        }

        // 2. transform
        lifecycleScope.launch {
            (1..3)
                .asFlow() // a flow of requests
                .transform {
                    // 다양한 형태로 emit이 가능하다.
                    emit("Making request $it")
                    emit(performRequest(it))
                    emit(it.toInt())
                }
                .collect {
                    Log.i("MYTAG", "${it} type is ${it::class.simpleName}")
                }
        }



    }

    private fun simple1() : Flow<Int> = flow {
        for(i in 1..3) {
            delay(1000L)
            emit(i)
        }
    }

    private fun simple2() : Flow<Int> = flow {
        Log.i("MYTAG", "Flow started")
        for(i in 1..3) {
            delay(1000L)
            emit(i)
        }
    }

    private fun simple3() : Flow<Int> = flow {
        for(i in 1..3) {
            delay(1000L)
            Log.i("MYTAG", "Emitting ${i}")
            emit(i)
        }
    }

    private fun simple4() {
        lifecycleScope.launch {
            (1..3)
                .asFlow()
                .collect {
                    Log.i("MYTAG", "${it}")
                }
        }
    }

    private suspend fun performRequest(request: Int) : String {
        delay(1000L)
        return "response ${request}"
    }
}