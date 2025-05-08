package com.hs.workation.fcm

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
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hs.workation.core.resource.R
import com.hs.workation.core.common.constants.FCMConstants
import com.hs.workation.core.model.fcm.CommonPayload
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.splash.main.view.activity.SplashActivity

class FCMService: FirebaseMessagingService() {

    private val CHANNEL_ID = "HSWorkation"
    private val CHANNEL_NAME = "일반 알림"
    private val CHANNEL_DESCRIPTION = "For 일반 알림"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() ${message.toString()}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() data : ${message.data}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() data.get(title) : ${message.data.get("title")}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() data.get(priority) : ${message.data.get("priority")}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() data.get(body) : ${message.data.get("body")}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() messageId : ${message.messageId}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() messageType : ${message.messageType}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() to : ${message.to}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() from : ${message.from}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() senderId : ${message.senderId}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() notification : ${message.notification}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() sentTime : ${message.sentTime}")
        LogUtil.d_dev("${javaClass.simpleName} onMessageReceived() rawData : ${message.rawData}")

        createNotificationChannel()

        val payloadModel = parser(message.data)

        when(payloadModel) {
            is CommonPayload -> {
                makeAndShowCommonNoti(payloadModel)
            }
        }

    }

    // 알림 채널 생성
    private fun createNotificationChannel() {
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.description = CHANNEL_DESCRIPTION
        notificationManager.createNotificationChannel(channel)
    }

    // 각 타입에 따라 파싱
    // Unit Test (test)
    fun parser(data: Map<String?, String?>): Any {
        val type = data["type"]?:""

        return when(type) {
            FCMConstants.FCM_TYPE_COMMON -> {
                val commonPayload = CommonPayload(
                    nick = data["nick"]?:"",
                    body = data["body"]?:"",
                    room = data["room"]?:""
                )
                commonPayload
            }
            else -> {

            }
        }
    }

    // 테스트 노티
    private fun makeAndShowCommonNoti(commonPayload: CommonPayload) {
        val notiId = System.currentTimeMillis().toInt()

        val intent = Intent(this, SplashActivity::class.java)
        intent.apply {
            putExtra(FCMConstants.FCM_TYPE_COMMON, commonPayload)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        // Android 12에서는 FLAG_MUTABLE or FLAG_IMMUTABLE 필수
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setColor(getColor(R.color.teal_200))
            .setContentTitle(commonPayload.nick)
            .setContentText(commonPayload.body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(commonPayload.room))

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        NotificationManagerCompat.from(this).notify(
            notiId,
            notificationBuilder.build()
        )
    }
}