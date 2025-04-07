package com.my.workmanagerdemo1.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.my.workmanagerdemo1.KEY_ONE_TIME_WORK_1_RESULT_1
import com.my.workmanagerdemo1.KEY_ONE_TIME_WORK_1_RESULT_2
import kotlinx.coroutines.delay

class OneTimeWork1(
    context: Context,
    workerParams: WorkerParameters,
): CoroutineWorker(context, workerParams) {

    private var count = 0

    override suspend fun doWork(): Result {
        try {

            for(i in 0 until 100) {
                Log.d("MYTAG", "${i}번")
                delay(300L)
                count = i
            }

            val outPutData = Data.Builder()
                .putString(KEY_ONE_TIME_WORK_1_RESULT_1, "${count}번")
                .putInt(KEY_ONE_TIME_WORK_1_RESULT_2, count)
                .build()

            return Result.success(outPutData)
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}