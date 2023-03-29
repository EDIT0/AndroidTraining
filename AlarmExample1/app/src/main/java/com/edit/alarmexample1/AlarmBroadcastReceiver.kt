package com.edit.alarmexample1

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmBroadcastReceiver : BroadcastReceiver() {

    private val CHANNEL_ID = "id"
    private val CHANNEL_NAME = "일반 알림"
    private val CHANNEL_DESCRIPTION = "For 일반 알림"

    private var id = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("MYTAG", "Received intent : ${intent?.getStringExtra("alarmType")}")
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            // Register alarm
//            intent.putExtra("alarmType", Alarm.REPEAT_ALARM)
            if (context != null) {
                Log.i("MYTAG", "BOOT_COMPLETED")
                val alarmList = PreferencesManager.getAlarmList(context)
                for(i in 0 until alarmList.size) {
                    if(alarmList[i].alarmType == AlarmType.ONE_TIME_ALARM_PER_DAY) {
                        if(alarmList[i].isSwitch) {
                            Log.i("MYTAG", "다시 알람 재등록 전: ${alarmList[i].id} / ${alarmList[i].remainingTime}")
                            val newAlarmData = AlarmModel(
                                alarmList[i].alarmType,
                                alarmList[i].id,
                                Alarm.stringDateToDate(Alarm.convertDateDayString(alarmList[i].finishDate)).toString(),
                                Alarm.calculateTime(Alarm.convertDateDayString(alarmList[i].finishDate)),
                                true
                            )
                            if(newAlarmData.remainingTime > 0) {
                                // 설정해놓은 시간이 안지났으면 알람 재등록
                                alarmList[i] = newAlarmData
                                Alarm.registerAlarmOneTimePerDay(context, newAlarmData.id, newAlarmData.remainingTime)
                            } else {
                                // 설정해놓은 시간이 지났으면 다음날 같은 시간 정보를 넣고 알람 오프
//                                newAlarmData.finishDate = Alarm.calculateTimeDate("${Alarm.convertDatePlusDayString("${alarmList[i].finishDate}")}").toString()
//                                newAlarmData.time = Alarm.calculateTime("${Alarm.convertDatePlusDayString("${alarmList[i].finishDate}")}")
                                newAlarmData.isSwitch = false
                                alarmList[i] = newAlarmData
                            }
                            Log.i("MYTAG", "다시 알람 재등록 후: ${alarmList[i].id} / ${alarmList[i].remainingTime}")
                            PreferencesManager.putAlarmList(context, alarmList)
                        }
                    } else if(alarmList[i].alarmType == AlarmType.REPEAT_ALARM_PER_DAY) {
                        if(alarmList[i].isSwitch) {
                            Log.i("MYTAG", "다시 알람 재등록 전: ${alarmList[i].id} / ${alarmList[i].remainingTime}")
                            val newAlarmData = AlarmModel(
                                alarmList[i].alarmType,
                                alarmList[i].id,
                                Alarm.stringDateToDate(Alarm.convertDateDayString(alarmList[i].finishDate)).toString(),
                                Alarm.calculateTime(Alarm.convertDateDayString(alarmList[i].finishDate)),
                                true
                            )
                            if(newAlarmData.remainingTime > 0) {
                                // 설정해놓은 시간이 안지났으면 알람 재등록
                                alarmList[i] = newAlarmData
                                Alarm.registerAlarmRepeatPerDay(context, newAlarmData.id, newAlarmData.remainingTime)
                            } else {
                                // 설정해놓은 시간이 지났으면 다음날 같은 시간 정보를 넣고 알람 오프
                                newAlarmData.finishDate = Alarm.stringDateToDate(Alarm.convertDatePlusDayString(alarmList[i].finishDate)).toString()
                                newAlarmData.remainingTime = Alarm.calculateTime(Alarm.convertDatePlusDayString(alarmList[i].finishDate))
                                newAlarmData.isSwitch = true
                                alarmList[i] = newAlarmData
                            }
                            Log.i("MYTAG", "다시 알람 재등록 후: ${alarmList[i].id} / ${alarmList[i].remainingTime}")
                            PreferencesManager.putAlarmList(context, alarmList)
                        }
                    } else if(alarmList[i].alarmType == AlarmType.ONE_TIME_ALARM_PER_TIME) {
                        if(alarmList[i].isSwitch) {
                            Log.i("MYTAG", "다시 알람 재등록 전: ${alarmList[i].id} / ${alarmList[i].remainingTime}")
                            val newAlarmData = AlarmModel(
                                alarmList[i].alarmType,
                                alarmList[i].id,
                                alarmList[i].finishDate,
                                alarmList[i].remainingTime,
                                true,
                                alarmList[i].repeatTime
                            )
                            Log.i("MYTAG", "${newAlarmData.remainingTime} / ${System.currentTimeMillis()}")
                            if((newAlarmData.remainingTime + (newAlarmData.repeatTime * 1000)) > System.currentTimeMillis()) {
                                // 설정해놓은 시간이 안지났으면 알람 재등록
                                newAlarmData.repeatTime = ((newAlarmData.remainingTime + (newAlarmData.repeatTime * 1000)) - System.currentTimeMillis()) / 1000
                                Log.i("MYTAG", "새롭게 계산된 남은 시간: ${newAlarmData.repeatTime}")
                                alarmList[i] = newAlarmData
                                Alarm.registerAlarmOneTimePerTime(context, newAlarmData.id, alarmList[i].repeatTime)
                            } else {
                                // 설정해놓은 시간이 지났으면 다음날 같은 시간 정보를 넣고 알람 오프
                                newAlarmData.isSwitch = false
                                alarmList[i] = newAlarmData
                            }
                            Log.i("MYTAG", "다시 알람 재등록 후: ${alarmList[i].id} / ${alarmList[i].remainingTime}")
                            PreferencesManager.putAlarmList(context, alarmList)
                        }
                    }
                }
            }
//            Log.i("MYTAG", "${context?.getSharedPreferences("ALARM_MANAGER", 0)?.getString("title", null)}")
        } else {
            val powerManager = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
            val wakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or
                        PowerManager.ACQUIRE_CAUSES_WAKEUP or
                        PowerManager.ON_AFTER_RELEASE, "My:Tag"
            )
            wakeLock.acquire(5000)

//        testAlarm(context)

            if (intent != null) {
                checkAlarmType(context, intent)
                createNotificationChannel(context)
                showNotification(context, intent)
            }
        }
    }

    private fun checkAlarmType(context: Context, intent: Intent) {
        Log.i("MYTAG", "${intent.getStringExtra("alarmType")}")
        if(intent.getStringExtra("alarmType") == AlarmType.REPEAT_ALARM_PER_DAY.toString()) {
            Log.i("MYTAG", "${intent.getIntExtra("id", 0)}")
            Log.i("MYTAG", "${intent.getLongExtra("time", 0)}")
//            Alarm.registerAlarmRepeat(context, intent.getIntExtra("requestCode", 0), intent.getLongExtra("time", 0))

            val alarmList = PreferencesManager.getAlarmList(context)

            for(i in 0 until alarmList.size) {
                if(intent.getIntExtra("id", 0) == alarmList[i].id){
                    val finishDate = alarmList[i].finishDate
                    alarmList[i].finishDate = Alarm.stringDateToDate(Alarm.convertDatePlusDayString(finishDate)).toString()
                    alarmList[i].remainingTime = Alarm.calculateTime(Alarm.convertDatePlusDayString(finishDate))
                    alarmList[i].isSwitch = true
                    PreferencesManager.putAlarmList(context, alarmList)
                    Alarm.registerAlarmRepeatPerDay(context, alarmList[i].id, alarmList[i].remainingTime)
                    return
                }
            }

        } else if(intent.getStringExtra("alarmType") == AlarmType.ONE_TIME_ALARM_PER_DAY.toString()) {
            val alarmList = PreferencesManager.getAlarmList(context)

            for(i in 0 until alarmList.size) {
                if(intent.getIntExtra("id", 0) == alarmList[i].id){
                    alarmList[i].isSwitch = false
                    PreferencesManager.putAlarmList(context, alarmList)
                    Alarm.unregisterAlarm(context, alarmList[i].id)
                    return
                }
            }
        } else if(intent.getStringExtra("alarmType") == AlarmType.ONE_TIME_ALARM_PER_TIME.toString()) {
            val alarmList = PreferencesManager.getAlarmList(context)

            for(i in 0 until alarmList.size) {
                if(intent.getIntExtra("id", 0) == alarmList[i].id){
                    alarmList[i].finishDate = alarmList[i].finishDate
                    alarmList[i].remainingTime = alarmList[i].remainingTime
                    alarmList[i].isSwitch = false
                    alarmList[i].repeatTime = alarmList[i].finishDate.toLong()
                    PreferencesManager.putAlarmList(context, alarmList)
                    Alarm.unregisterAlarm(context, alarmList[i].id)
                    return
                }
            }
        }
    }

    // 알림 채널 생성
    private fun createNotificationChannel(context: Context) {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = CHANNEL_DESCRIPTION
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(context: Context, intent: Intent) {
        id = (intent.getIntExtra("id", 0) + 1000)
        NotificationManagerCompat.from(context).notify(id, createNotification(context, id, "${intent.getIntExtra("id", 0)}", "Type: ${intent.getStringExtra("alarmType")}\nTime: ${intent.getLongExtra("time", 0)}"))
    }

    private fun createNotification(
        context: Context,
        id: Int,
        title: String,
        message: String
    ): Notification {
        val intent = Intent(context, MainActivity::class.java)
        intent.apply {
//            putExtra("content_Idx", contentIdx)
//            putExtra("profileImage_sender", profileImageSender)
//            putExtra("type", type)
//            putExtra("parentReplyIdx", parentReplyIdx)
//            putExtra("childReplyIdx", childReplyIdx)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        // Android 12에서는 FLAG_MUTABLE or FLAG_IMMUTABLE 필수
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        }

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
//            .setColor(getColor(R.color.notification_color))
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(message))

        return notificationBuilder.build()
    }
}

