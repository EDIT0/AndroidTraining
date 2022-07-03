package com.example.coroutinestudy

import kotlinx.coroutines.*

fun main() {
//    GlobalScope.launch {
//        val userData = fetchUserData()
//        val userCache = cacheUserData(userData)
//        updateTextView(userCache)
//    }

    runBlocking {
        val job = CoroutineScope(Dispatchers.IO).launch {
            println(abc())
            println(def())
            println(ghi())
        }
        job.join()
    }



}

suspend fun fetchUserData() = "user_name"
suspend fun cacheUserData(user: String) = user
fun updateTextView(user: String) = user

suspend fun abc() : String {
    delay(3000L)
    return "abc"
}

suspend fun def() : String {
    return "def"
}

suspend fun ghi() : String {
    delay(500L)
    return "ghi"
}