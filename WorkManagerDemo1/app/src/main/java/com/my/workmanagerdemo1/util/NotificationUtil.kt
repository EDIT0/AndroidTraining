package com.my.workmanagerdemo1.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.my.workmanagerdemo1.MainActivity
import com.my.workmanagerdemo1.R

object NotificationUtil {

    // 알림 채널 생성
    fun createNotificationChannel(context: Context) {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("CHANNEL_ID", "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "CHANNEL_DESCRIPTION"
        notificationManager.createNotificationChannel(channel)
    }

    fun makeNoti(context: Context, percentage: Int) {
        val notiId = System.currentTimeMillis().toInt()

        val intent = Intent(context, MainActivity::class.java)
//        intent.apply {
//            putExtra(FCMConstant.FCM_TYPE, commentPayload.postType?:"")
//            putExtra(FCMConstant.FCM_TYPE_COMMENT, commentPayload)
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//        }

        // Android 12에서는 FLAG_MUTABLE or FLAG_IMMUTABLE 필수
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationBuilder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setColor(ContextCompat.getColor(context, R.color.black))
            .setContentTitle("진행률")
            .setContentText("${percentage}% ...")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

//        notificationBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(commentPayload.body))

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify(
            notiId,
            notificationBuilder.build()
        )
    }
}