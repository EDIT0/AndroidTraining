package com.example.coroutinestudy

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() {

    /**
     * lauch: 코루틴 빌더에서 코루틴이 만들어짐
     * 코루틴은 코루틴스코프에서 실행이 된다.
     * GlobalScope: 전역스코프
     *
     * lauch는 자신을 호출한 스레드를 블록킹하지 않음
     * runBlocking은 블록킹함
     *
     * Coroutine builder
     * -> launch
     * -> runBlocking
     *
     * Scope
     * -> CoroutineScope
     * -> GlobalScope
     *
     * Suspend function
     * -> suspend
     * -> delay()
     * -> join()
     *
     * Structured concurrency
     * */

    // 1
//    launchToThread()
    // 2
//    threadSleepToRunBlocking()
    // 3
//    essentialCode()
    // 4
//    waitingJobExceptToDelay()
    // 5
//    structuredConcurrency()
    // 6
//    suspendFunction()
    // 7
//    howLightWeightIsCoroutine()
    // 8
//    a()
    // 9
    stopAndPause()
}

fun launchToThread() {
    thread {
        Thread.sleep(3000L) // non-blocking
        println("World!")
    }

    println("Hello")
    Thread.sleep(6000L)
}

fun threadSleepToRunBlocking() {
    GlobalScope.launch {
        delay(3000L) // non-blocking
        println("World!")
    }

    println("Hello")
    runBlocking {
        delay(6000L) // 6초 동안 코루틴 살아있도록 *즉, 메인스레드 6초 블록킹*
    }
}

fun essentialCode() {
    runBlocking {
        GlobalScope.launch {
            delay(3000L) // non-blocking
            println("World!")
        }

        println("Hello")
        delay(6000L) // 6초 동안 코루틴 살아있도록 *즉, 메인스레드 6초 블록킹*
    }
}

fun waitingJobExceptToDelay() {
    runBlocking {
        val job = GlobalScope.launch {
            delay(3000L) // non-blocking
            println("World!")
        }
        println("Hello")
        job.join() // job이 완료될 때까지 이 자리에서 기다림. 즉, 얼마나 걸릴지도 모르는 함수에 delay를 주지 않아도 완료를 보장해준다.
    }
}

fun structuredConcurrency() { // 구조를 이용한 방법
    runBlocking { // 엄마코루틴(runBlocking)이 자식코루틴(launch)을 기다려주기 때문에 Hello World!가 다 찍힌다.
        launch { // runBlocking에서 launch를 해준다.
            delay(2000L)
            println("1 World!")
        }

        launch { // runBlocking에서 launch를 해준다.
            delay(1000L)
            println("2 World!")
        }

        launch { // runBlocking에서 launch를 해준다.
            delay(3000L)
            println("3 World!")
        }
        println("Hello")
    }
}

fun suspendFunction() {
    runBlocking {
        launch {
            myWorld()
        }
        println("Hello")
    }
}

suspend fun myWorld() { // suspend는 suspend함수나 코루틴에서만 실행이 가능하다. 그래서 (코루틴 내부라)delay도 먹히는 것
    delay(3000L)
    println("World!")
}

// 이건 잘 이해가 안감
fun howLightWeightIsCoroutine() {
    runBlocking {
        repeat(100_100) {
            delay(1000L)
            print(".")
        }
    }
}

fun a() {
    runBlocking {
        GlobalScope.launch {
            repeat(1000) {
                println("I'm sleeping ${it}")
                delay(500L)
            }
        }
        delay(1300L) // 메인 스레드가 1.3초 뒤에 끝난다. 그래서 GlobalScope은 runBlocking과 관계(부모, 자식)가 없기 때문에 함께 1.3초 후에 끝난다.
    }
}

fun stopAndPause() {
    /**
     * 모두 메인 스레드에서 시작 (하나의 스레드만 사용한다는 말)
     * 처음 A의 0이 찍히고, B가 모두 찍힌 후, 나머지 A가 1~499까지 찍힌다.
     * 코루틴은 중단됐다가 다시 실행될 수 있기 때문에 A가 10L 쉬는 동안 B가 치고 들어온다.
     * B는 중단점이 없으므로 계속 본인 일은 쭈욱 실행한다.
     * B가 끝난 후 나머지 A가 다 실행된다.
     * */
    runBlocking { // 첫번째 코루틴 생성
        launch { // 두번째 코루틴은 여기서 생성
            repeat(500) {
                println("Coroutine A, ${it}")
                delay(10L) // 일시중단 가능 함수
            }
        }

        launch { // 세번째 코루틴은 여기서 생성
            repeat(500) {
                println("Coroutine B, ${it}")
//                delay(10L) // 이 부분을 활성화 하면 번갈아가면서 찍힌다. 10L씩 쉬어주기 때문이다.
            }
        }

        println("Coroutine Outer") // 첫번째 코루틴으로 이 부분을 찍는다.
    }
}

fun <T>println(msg: T) {
    kotlin.io.println("${msg} [${Thread.currentThread().name}]")
}