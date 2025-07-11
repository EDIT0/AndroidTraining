package com.my.workmanagerdemo1.work

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.my.workmanagerdemo1.KEY_ONE_TIME_WORK_1_RESULT_1
import com.my.workmanagerdemo1.KEY_ONE_TIME_WORK_1_RESULT_2
import com.my.workmanagerdemo1.R
import com.my.workmanagerdemo1.util.SharedPreferencesUtil
import com.my.workmanagerdemo1.util.SharedPreferencesUtil.KEY_PERCENTAGE
import kotlinx.coroutines.delay

class NoLimitWork1(
    val context: Context,
    workerParams: WorkerParameters,
): CoroutineWorker(context, workerParams) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private var totalLength = 200
    private var count = 0

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        createNotificationChannel()

        try {
            // 포그라운드 노티 띄우기
            setForeground(createForegroundNotification(0))

            val message = inputData.getString("key1") ?: "No Message"
            val number = inputData.getInt("key2", 0)

            for(i in number until totalLength+1) {
                Log.d("MYTAG", "No limit work ${message} ${i}번")
                delay(1000L)
                // 호출한 코루틴 소비자에게 데이터 방출하기
                val progressData = Data.Builder()
                    .putInt("progress", (i * 100) / totalLength)
                    .build()
                setProgress(progressData)
                // 현재 진행률 저장
                SharedPreferencesUtil.putInt(KEY_PERCENTAGE, i)
                // 현재 진행률에 대한 백분율 업데이트
                updateNotification(progress = (i * 100) / totalLength)

                count = i

                // 초기화
                if(totalLength == number) {
                    SharedPreferencesUtil.remove(KEY_PERCENTAGE)
                }
            }

            val outPutData = Data.Builder()
                .putString(KEY_ONE_TIME_WORK_1_RESULT_1, "${count}번")
                .putInt(KEY_ONE_TIME_WORK_1_RESULT_2, count)
                .build()

            count = 0

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
            .setContentTitle("NoLimitWork1")
            .setContentText("작업 시작")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    private fun createForegroundNotification(percentage: Int): ForegroundInfo {
        val notification = NotificationCompat.Builder(context, "work_channel")
            .setContentTitle("NoLimitWork1")
            .setContentText("작업 실행 중... ${percentage}%")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()

        return ForegroundInfo(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
    }

    private fun createNotificationWithProgressBar(progress: Int): Notification {
        return NotificationCompat.Builder(context, "work_channel")
            .setContentTitle("NoLimitWork1")
            .setContentText("작업 실행 중... ${progress}%")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setProgress(100, progress, false)
            .build()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun updateNotification(progress: Int) {
        val notification = createNotificationWithProgressBar(progress)
        NotificationManagerCompat.from(context).notify(1, notification)
    }
}