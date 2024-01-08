package com.edit.skeletonloading

import android.util.Log
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    val channel = produce<Int> {
        send(1)
    }


    channel.consumeAsFlow().collect {
        System.out.println("A " + it)
    }

    launch {
        for(i in channel) {
            System.out.println("B " + i)
        }
    }
//    channel.consume {
//        Log.d("MYTAG", "${this}")
//    }
}