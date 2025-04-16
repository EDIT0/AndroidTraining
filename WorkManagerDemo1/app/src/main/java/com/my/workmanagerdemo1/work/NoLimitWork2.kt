package com.my.workmanagerdemo1.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.my.workmanagerdemo1.KEY_NO_LIMIT_WORK_1_RESULT_1
import com.my.workmanagerdemo1.KEY_NO_LIMIT_WORK_1_RESULT_2
import com.my.workmanagerdemo1.KEY_ONE_TIME_WORK_1_RESULT_1
import com.my.workmanagerdemo1.KEY_ONE_TIME_WORK_1_RESULT_2
import com.my.workmanagerdemo1.R
import kotlinx.coroutines.delay

class NoLimitWork2(
    val context: Context,
    workerParams: WorkerParameters,
): CoroutineWorker(context, workerParams) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private var count = 0

    override suspend fun doWork(): Result {
        createNotificationChannel()

        try {
            setForeground(createForegroundNotification())

            val message = inputData.getString("key1") ?: "No Message"
            val number = inputData.getInt("key2", 0)

            for(i in 0 until 1020) {
                Log.d("MYTAG", "No limit work ${message} ${i}번")
                delay(1000L)
                val progressData = Data.Builder()
                    .putInt("progress", (i * 100) / 1020)
                    .build()
                setProgress(progressData)
                count = i
            }

            val outPutData = Data.Builder()
                .putString(KEY_NO_LIMIT_WORK_1_RESULT_1, "${count}번")
                .putInt(KEY_NO_LIMIT_WORK_1_RESULT_2, count)
                .build()

            return Result.success(outPutData)
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "work_channel",
                "WorkManager Start Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun createNotification(): Notification {
        return NotificationCompat.Builder(context, "work_channel")
            .setContentTitle("NoLimitWork2")
            .setContentText("작업 시작")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    private fun createForegroundNotification(): ForegroundInfo {
        val notification = NotificationCompat.Builder(context, "work_channel")
            .setContentTitle("NoLimitWork2")
            .setContentText("작업 실행 중...")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()

        return ForegroundInfo(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
    }
}