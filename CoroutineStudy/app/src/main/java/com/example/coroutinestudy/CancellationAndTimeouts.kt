package com.example.coroutinestudy

import kotlinx.coroutines.*
import java.lang.Exception

fun main() {

    /**
     * cancel()을 호출한다고 다 취소되는게 아님
     * cancellation에 협조적이여야 함
     * 협조적인 방법 1. suspend 함수를 주기적으로 호출하면서, 다시 suspend 함수가 재개 될 때 exception을 발생시키는 방식 (delay(1L), yield())
     * 협조적인 방법 2. isActive를 체크해서 취소시켜주는 방식
     * */

    // 1
//    cancellingCoroutinesExecution()
    // 2
//    cancellationIsCooperative()
    // 3
//    makingComputationCodeCancellable()
    // 4
//    closingResourcesWithFinally()
    // 5
//    runNonCancellableBloc()
    // 6
//    timeout()
    // 7
    withTimeoutOrNull()
}

fun cancellingCoroutinesExecution() {
    runBlocking {
        val job = launch {
            repeat(1000) { i ->
                println("job: I'm sleeping $i")
                delay(500L)
            }
        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        job.cancel() // 1.3초 뒤에 cancel()되어 끝난다.
        job.join()
        println("main: Now I can quit.")
    }
}

fun cancellationIsCooperative() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            try {
                var nextPrintTime = startTime
                var i = 0
                while (i < 5) {
                    if(System.currentTimeMillis() >= nextPrintTime) {
//                    delay(1L) // 일시중단을 하는데에 협조적이게 되었음(suspend)
                        yield() // 일시중단을 하는데에 협조적이게 되었음(suspend) 내부적으로 exception을 발생시켜서, 사용 시에 exception 에러가 발생하는걸 볼 수 있음
                        println("job: I'm sleeping ${i++}")
                        nextPrintTime += 500L
                    }
                }
            } catch (e : Exception) {
                println("Exception : ${e}")
            }

        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}

fun makingComputationCodeCancellable() {
    // isActive를 활용해 코루틴의 활성 상태를 알 수 있다.
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            println("isActive: ${isActive}")
            while (isActive) {
                if(System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++}")
                    nextPrintTime += 500L
                }
            }
            println("isActive: ${isActive}")
        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}

fun closingResourcesWithFinally() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i")
                    delay(500L)
                }
            } catch (e : Exception) {
                println("Exception: ${e}")
            } finally {
                // resource 해제
                println("job: I'm running finally")
            }
        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancel() 하면 suspend 함수를 재개하면서 exception을 발생시킴
        println("main: Now I can quit.")
    }
}

fun runNonCancellableBloc() {
    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i")
                    delay(500L)
                }
            } catch (e : Exception) {
                println("Exception: ${e}")
            } finally {
                // resource 해제
                withContext(NonCancellable) { // cancel()된 코루틴을 다시 살린다.
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And i've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancel() 하면 suspend 함수를 재개하면서 exception을 발생시킴
        println("main: Now I can quit.")
    }
}

fun timeout() {
    runBlocking {
        withTimeout(1300L) { // exception 던짐
            repeat(1000) { i ->
                println("I'm sleeping $i")
                delay(500L)
            }
        }
    }
}

fun withTimeoutOrNull() {
    runBlocking {
        val result = withTimeoutOrNull(1300L) { // null 반환해줌
            repeat(1000) { i ->
                println("I'm sleeping $i")
                delay(500L)
            }
            "Done"
        }
        println("Result is $result")
    }
}