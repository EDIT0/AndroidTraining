package com.my.workmanagerdemo1.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.my.workmanagerdemo1.R
import com.my.workmanagerdemo1.SharedViewModel
import com.my.workmanagerdemo1.util.getSharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForegroundService1: LifecycleService() {

    private lateinit var sharedVM: SharedViewModel

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val scope = lifecycleScope
    private var scopeJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        // 첫 생성 시 호출
        Log.d("MYTAG", "${javaClass.simpleName} onCreate()")

        sharedVM = getSharedViewModel()

        createNotificationChannel()
        startForeground(10, createNotification())

    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("MYTAG", "${javaClass.simpleName} onBind()")
        return super.onBind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Log.d("MYTAG", "${javaClass.simpleName} onStartCommand()")

        scopeJob?.cancel()
        scopeJob = scope.launch {
            for(i in 0 .. 5) {
                Log.i("MYTAG", "ForegroundService1 ${i}")

                delay(1000L)
                Log.i("MYTAG", "${sharedVM.data1} / ${sharedVM.data2}")
                sharedVM.data1 = "data${i}"
                sharedVM.data2 = "${i}data"
            }

            sharedVM.commonSharedFlow.collect {
                Log.i("MYTAG", "${this@ForegroundService1.javaClass.simpleName} ${it}")
            }
        }


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("MYTAG", "${javaClass.simpleName} onDestroy()")
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
        return NotificationCompat.Builder(this, "work_channel")
            .setContentTitle("ForegroundService1")
            .setContentText("작업 시작")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }
}