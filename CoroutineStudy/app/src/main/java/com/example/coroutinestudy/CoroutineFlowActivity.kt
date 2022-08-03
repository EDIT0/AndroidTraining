package com.example.coroutinestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestudy.databinding.ActivityCoroutineFlowBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

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
//        lifecycleScope.launch {
//            (1..3)
//                .asFlow() // a flow of requests
//                .transform {
//                    // 다양한 형태로 emit이 가능하다.
//                    emit("Making request $it")
//                    emit(performRequest(it))
//                    emit(it.toInt())
//                }
//                .collect {
//                    Log.i("MYTAG", "${it} type is ${it::class.simpleName}")
//                }
//        }

        // 3. .take() emit실행 갯수를 정함 (즉, 중간에 취소가 되고 그로 인한 예외가 발생하기 때문에 예외처리문으로 작성한다.
//        lifecycleScope.launch {
//            simple5()
//                .take(2)
//                .collect {
//                    Log.i("MYTAG", "${it}")
//                }
//        }

        /**
         * Terminal flow operator
         * */
//        toList 또는 toSet : Flow를 Mutable Collection으로 변환
        lifecycleScope.launch {
//            simple6() // reduce : 첫 번째 원소부터 주어진 operation을 이용하여 누적시키면서 최종값을 반환
//            simple7() // fold : 초기 값을 입력받아 주어진 operation을 이용하여 누적시키면서 최종값을 반환
//            simple8() // first : 첫 번째 원소를 반환하고 나머지는 Cancel, 첫 번째 요소만 처리할 때
//            simple9() // collectIndexed : collect와 같은 동작이지만 index 요소가 추가되어 원하는 index에 맞는 처리가 가능
        }

        /**
         * Flows are sequential
         * */
        lifecycleScope.launch {
            // 특수 연산자가 중간에 개입되지 않는 한 하나의 스레드로 순차 실행
//            simple10()
        }

        /**
         * Flow context
         * */
        // withContext(context) 항상 어떠한 코루틴 안에서 실행된다. 어떠한 코루틴(main) 내에서 withContext()를 사용하여 내가 원하는 Context로 바꿔서 실행 할 수 있다.
        lifecycleScope.launch {
//            Log.i("MYTAG", "1. ${Thread.currentThread().name}")
//            withContext(Dispatchers.IO) {
//                Log.i("MYTAG", "2. ${Thread.currentThread().name}")
//                simple11()
//                    .collect {
//                        Log.i("MYTAG", "${it} / ${Thread.currentThread().name}")
//                    }
//            }
//            Log.i("MYTAG", "3. ${Thread.currentThread().name}")
        }

        // withContext() 잘못된 사용법 (is not allowed to emit from a different context)
        lifecycleScope.launch {
//            simple12().collect {
//                Log.i("MYTAG", "${it}")
//            }
        }

        // .flowOn(context) 원하는 context로 변경하며 실행
        lifecycleScope.launch {
//            simple13()
//                .collect {
//                    Log.i("MYTAG", "${it} / ${Thread.currentThread().name}")
//                }
        }



        // buffer()를 사용하면 방출(emit)하고 collect(수집, 소비 할 때)하고 방출, 수집, 방출, 수집 기다리지 않는다. (그러나 시간이 얼마나 걸리든 일단 순차적으로 다 실행함)
        // collect(수집)이 오래 걸리더라도 emit은 미리 다 시켜둔다.
        lifecycleScope.launch {
//            val time = measureTimeMillis {
//                simple14()
//                    .buffer() // buffer emissions, don't wait
//                    .collect {
//                        delay(500L)
//                        Log.i("MYTAG", "${it} / ${Thread.currentThread().name}")
//                    }
//            }
//            Log.i("MYTAG", "Collected in ${time} ms / ${Thread.currentThread().name}")
        }

        // conflate()는 가장 최근 업데이트를 보여준다. (즉, emit 1 -> collect 1 이 후, emit 2 , emit 3 -> collect 3. emit2가 무시되고 가장 최근의 emit 3이 소비된다)
        // Ex) emit1 (100L) !collect1 (500L)! emit2 (100L) emit3(100L) -> emit1,2,3까지 300L 정도 걸림 그러므로 emit1끝나고 2는 생략 3으로 바로 collect
        lifecycleScope.launch {
//            val time = measureTimeMillis {
//                simple14()
//                    .conflate()
//                    .collect {
//                        delay(500L)
//                        Log.i("MYTAG", "${it} / ${Thread.currentThread().name}")
//                    }
//            }
//            Log.i("MYTAG", "Collected in ${time} ms / ${Thread.currentThread().name}")
        }

        // conflate() -> collectLatest()
        // Ex) emit1 (100L) !collect1 (500L)! emit2 (100L) emit3 (100L) -> emit1,2,3까지 300L 정도 걸림 그러나 emit1이 소비되는 시간을 기다리지 않고(중단 후) 캔슬 후 emit3으로 바로 collect
        lifecycleScope.launch {
            val time = measureTimeMillis {
                simple14()
                    .collectLatest {
                        Log.i("MYTAG", "Collecting: ${it} / ${Thread.currentThread().name}")
                        delay(500L)
                        Log.i("MYTAG", "Done: ${it} / ${Thread.currentThread().name}")
                    }
            }
            Log.i("MYTAG", "Collected in ${time} ms / ${Thread.currentThread().name}")
        }

    }

    private fun simple1(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(1000L)
            emit(i)
        }
    }

    private fun simple2(): Flow<Int> = flow {
        Log.i("MYTAG", "Flow started")
        for (i in 1..3) {
            delay(1000L)
            emit(i)
        }
    }

    private fun simple3(): Flow<Int> = flow {
        for (i in 1..3) {
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

    private suspend fun performRequest(request: Int): String {
        delay(1000L)
        return "response ${request}"
    }

    private suspend fun simple5(): Flow<Int> {
        return flow {
            try {
                emit(1)
                emit(2)
                Log.i("MYTAG", "This line will not execute")
                emit(3)
            } catch (e: Exception) {
                Log.i("MYTAG", "error ${e.message}")
            } finally {
                Log.i("MYTAG", "Finally in numbers")
            }
        }
    }

    private suspend fun simple6() {
        val sum =
            (1..5)
                .asFlow()
                .map {
                    Log.i("MYTAG", "map : ${it}")
                    it * it
                }
                .reduce { accumulator, value ->
                    Log.i("MYTAG", "accumulator: ${accumulator} / value: ${value}")
                    accumulator + value
                }
        Log.i("MYTAG", "sum : ${sum}")
    }

    private suspend fun simple7() {
        val sum =
            (1..5)
                .asFlow()
                .fold(5) { acc, value ->
                    Log.i("MYTAG", "acc: ${acc} / value: ${value}")
                    acc + value
                }

        Log.i("MYTAG", "sum : ${sum}")
    }

    private suspend fun simple8() {
        val sum =
            (1..5)
                .asFlow()
                .filter {
                    it == 7
                }
                .firstOrNull() // 첫번째 리턴 요소가 없을 시, null 리턴
        Log.i("MYTAG", "sum : ${sum}")
    }

    private suspend fun simple9() {
        (1..3)
            .asFlow()
            .collectIndexed { index, value ->
                Log.i("MYTAG", "index: ${index} / value: ${value}")
            }
    }

    private suspend fun simple10() {
        (1..5).asFlow()
            .filter {
                Log.i("MYTAG","Filter: ${it} / CurrentThread: ${Thread.currentThread().name}")
                it % 2 == 0
            }
            .map {
                Log.i("MYTAG","Map: ${it} / CurrentThread: ${Thread.currentThread().name}")
                "string $it"
            }.collect {
                Log.i("MYTAG","Collect: ${it} / CurrentThread: ${Thread.currentThread().name}")
            }
    }

    private suspend fun simple11() : Flow<Int> = flow {
        Log.i("MYTAG","Started simple flow")
        for (i in 1..3) {
            emit(i)
        }
    }

    private suspend fun simple12() : Flow<Int> = flow {
        // 잘못된 사용법
        // flow 내에서는 is not allowed to emit from a different context.
        withContext(Dispatchers.Default) {
            for(i in 1..3) {
                emit(i)
            }
        }
    }

    private suspend fun simple13() : Flow<Int> = flow {
        // flow는 background thread에서 동작한다.
        for(i in 1..3) {
            delay(100)
            Log.i("MYTAG", "Emitting ${i} / ${Thread.currentThread().name}")
            emit(i)
        }
    }.flowOn(Dispatchers.Default) // context를 원하는 context로 바꿔가면서 실행

    private suspend fun simple14(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // pretend we are asynchronously waiting 100 ms
            emit(i) // emit next value
            Log.i("MYTAG", "emit: ${i}")
        }
    }

}