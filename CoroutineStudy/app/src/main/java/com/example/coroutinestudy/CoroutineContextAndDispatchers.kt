package com.example.coroutinestudy

import kotlinx.coroutines.*

fun main() {
    // 1
//    dispatchersAndThreads()
    // 2
//    debuggingCoroutineAndThreads()
    // 3
    jumpingBetweenThreads()
    // 4
//    jobInTheContext()
    // 5
//    childrenOfACoroutine()
    // 6
//    parentalResponsibilities()
    // 7
//    combiningContextElements()
    // 8
//    coroutineScope()
}

fun dispatchersAndThreads() {
    runBlocking {
        launch {
            println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {
            println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            println("Default: I'm working in thread ${Thread.currentThread().name}")
        }

        newSingleThreadContext("MyOwnThread").use {
            launch(it) {
                println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
            }
        }
    }
}

fun debuggingCoroutineAndThreads() {
    runBlocking {
        val a = async {
            println("I'm computing a piece of the answer")
            6
        }
        val b = async {
            println("I'm computing another piece of the answer")
            7
        }
        println("The answer is ${a.await() * b.await()}")
    }
}

fun jumpingBetweenThreads() {
    // .use가 알아서 close해줌
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                println("Started in ctx1")
                withContext(ctx2) { // withContext(여기 들어간 스레드에서 실행된다) 즉, withContext를 사용하면 스레드를 점프해서 스위치 할 수 있다.
                    println("Working in ctx2")
                }
                println("Back to ctx1")
            }
        }
    }
}

fun jobInTheContext() {
    runBlocking {
        println("My job is ${coroutineContext[Job]}")

        launch {
            println("My job is ${coroutineContext[Job]}")
        }

        async {
            println("My job is ${coroutineContext[Job]}")
        }

        coroutineContext[Job]?.isActive ?: true // == isActive

    }
}

fun childrenOfACoroutine() {
    runBlocking {
        var request = launch {
            GlobalScope.launch { // 자식 아님 (별도임)
                println("job1: I run in GlobalScope and execute independently!")
                delay(1000L)
                println("job1: I am not affected by cancellation of the request")
            }
            launch { // 자식임
                delay(100L)
                println("job2: I am a child of the request coroutine")
                delay(1000L)
                println("job2: I will not execute this line if my parent request is cancelled")
            }
        }
        delay(500L)
        request.cancel()
        delay(1000L)
        println("main: Who has survived request cancellation?")
    }
}

fun parentalResponsibilities() {
    runBlocking {
        val request = launch {
            repeat(3) { i ->
                launch {
                    delay((i + 1) * 200L)
                    println("Coroutine $i is done")
                }
            }
//            delay(500L)
            println("request: I'm done and I don't explicitly join my children that are still active")
        }
        request.join()
        println("Now processing of the request is complete")
    }
}

fun combiningContextElements() {
    runBlocking {
        launch(Dispatchers.Default + CoroutineName("test")) {
            println("I'm working in thread ")
        }
    }
}

fun coroutineScope() {
    runBlocking {
        val activity = Activity()

        activity.doSomething()
        println("Launched coroutines")
        delay(500L)
        println("Destroying activity!")
        activity.destroy()
        delay(1000L)
    }
}

class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default) // 테스트 목적으로 Default 사용

    fun destroy() {
        mainScope.cancel()
    }

    fun doSomething() {
        repeat(10) { i ->
            mainScope.launch {
                delay((i + 1) * 200L)
                println("Coroutine ${i} is done")
            }
        }
    }
}