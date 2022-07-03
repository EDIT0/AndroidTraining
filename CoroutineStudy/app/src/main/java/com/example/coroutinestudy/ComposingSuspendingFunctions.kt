package com.example.coroutinestudy

import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.system.measureTimeMillis

fun main() {
    // 1
//    sequentialByDefault()
    // 2
//    concurrentUsingAsync()
    // 3
//    lazilyStartedAsync()
    // 4
//    asyncStyleFunctions()
    // 5
//    structuredConcurrencyWithAsync()
    // 6
    cancellationPropagatedCoroutinesHierarchy()
}

fun sequentialByDefault() {
    // 일반 코드처럼 작성해도 이게 비동기여도 순차적으로 실행되는게 기본이다.
    runBlocking {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in ${time} ms")
    }
}

suspend fun doSomethingUsefulOne(): Int {
    println("start-1")
    delay(3000L)
    println("end-1")
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    println("start-2")
    delay(1000L)
    println("end-2")
    return 29
}

fun concurrentUsingAsync() {
    runBlocking {
        val time = measureTimeMillis {
            val one = async { // sequentialByDefault에서는 suspend 됐다가 resume 될 때까지 이 라인에서 기다렸지만, async는 내부 블록을 실행하고 바로 다음 줄로 넘어간다.
                doSomethingUsefulOne()
            }
//            val oneResult = one.await()

            val two = async {
                doSomethingUsefulTwo()
            }
            println("The answer is ${one.await() + two.await()}") // await()는 async가 끝날 때 까지 기다린다.
//            println("The answer is ${oneResult + two.await()}") // await()는 async가 끝날 때 까지 기다린다.
        }
        println("Completed in ${time} ms")
    }
}

fun lazilyStartedAsync() {
    runBlocking {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { // LAZY는 await(), start()를 해줄 경우 실행된다.
                doSomethingUsefulOne()
            }
            val two = async(start = CoroutineStart.LAZY) {
                doSomethingUsefulTwo()
            }
            one.start() // start()를 해주면 바로 다음 줄을 실행하므로 총 실행시간이 단축된다.
            two.start() // 그러나 start()가 없다면 await()에서 실행이 되는데, await() 특성상 async{} 내부 블록 코드가 끝나는걸 기다리므로 총 실행시간이 단축되지 않는다.
            println("The answer is ${one.await() + two.await()}") // await()는 async가 끝날 때 까지 기다린다.
        }
        println("Completed in ${time} ms")
    }
}

fun asyncStyleFunctions() { // 이렇게 짜면 안된다는 예시
    val time = measureTimeMillis {
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()

        println("My Exception")
        throw Exception("My Exception") // exception이 발생해도 끝나지 않음

        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in ${time} ms")
}

fun somethingUsefulOneAsync() = GlobalScope.async {
    println("start, somethingUsefulOneAsync()")
    doSomethingUsefulOne()
//    println("end, somethingUsefulOneAsync()")

}
fun somethingUsefulTwoAsync() = GlobalScope.async {
    println("start, somethingUsefulTwoAsync()")
    doSomethingUsefulTwo()
//    println("end, somethingUsefulTwoAsync()")
}

fun structuredConcurrencyWithAsync() { // asyncStyleFunctions 예제의 바른 예
    runBlocking {
        val time = measureTimeMillis {
            println("The answer is ${concurrentSum()}")
        }
        println("Completed in ${time} ms")
    }
}

suspend fun concurrentSum() : Int = coroutineScope {
    val one = async {
        doSomethingUsefulOne()
    }

    val two = async {
        doSomethingUsefulTwo()
    }

    delay(2000L)
    println("My Exception")
    throw Exception("My Exception") // exception이 발생해도 끝나지 않음

    one.await() + two.await()
}

fun cancellationPropagatedCoroutinesHierarchy() {
    runBlocking {
        try {
            val result = failedConcurrentSum() // 실행 중에 exception이 발생하면 모든 코루틴들이 종료 될 수 있다. 즉, 리소스를 다 해제하고 종료한다는 것.
            println(result)
        } catch (e : ArithmeticException) {
            println("Computation failed with ArithmeticException")
        }
    }
}

suspend fun failedConcurrentSum() : Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE)
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}