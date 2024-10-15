package com.my.cardocrdemo1.util

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object CameraUtil {
    suspend fun Context.getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(this).apply {
                addListener(
                    Runnable {
                        continuation.resume(get())
                    },
                    ContextCompat.getMainExecutor(this@getCameraProvider)
                )
            }
        }

    val Context.executor: Executor
        get() = ContextCompat.getMainExecutor(this)
}