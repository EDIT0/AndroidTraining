package com.edit.alarmexample1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

object Alarm {

    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent

    fun makeAlarmManager(context: Context) {
        alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
    }

    fun registerAlarmRepeatPerDay(context: Context, requestCode: Int, time: Long){
        if(!::alarmManager.isInitialized) {
            makeAlarmManager(context)
        }

        val intent1 = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra("alarmType", AlarmType.REPEAT_ALARM_PER_DAY.toString())
            putExtra("id", requestCode)
            putExtra("time", time)
        }
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent1, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val repeatInterval: Long = time * 1000
        val triggerTime = (SystemClock.elapsedRealtime() + repeatInterval)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)

//        Toast.makeText(context, "Exact periodic Alarm On", Toast.LENGTH_SHORT).show()
    }

    fun registerAlarmOneTimePerTime(context: Context, requestCode: Int, time: Long){
        if(!::alarmManager.isInitialized) {
            makeAlarmManager(context)
        }

        val intent1 = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra("alarmType", AlarmType.ONE_TIME_ALARM_PER_TIME.toString())
            putExtra("id", requestCode)
            putExtra("time", time)
        }
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent1, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val repeatInterval: Long = time * 1000
        val triggerTime = (SystemClock.elapsedRealtime() + repeatInterval)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)

//        Toast.makeText(context, "Exact periodic Alarm On", Toast.LENGTH_SHORT).show()
    }

    fun unregisterAlarm(context: Context, requestCode: Int) {
        if(!::alarmManager.isInitialized) {
            makeAlarmManager(context)
        }

        pendingIntent = PendingIntent.getBroadcast(context, requestCode, Intent(context, AlarmBroadcastReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        alarmManager.cancel(pendingIntent)
//        Toast.makeText(context, "Exact periodic Alarm Off", Toast.LENGTH_SHORT).show()
    }

    fun registerAlarmOneTimePerDay(context: Context, requestCode: Int, time: Long){
        if(!::alarmManager.isInitialized) {
            makeAlarmManager(context)
        }

        val intent1 = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra("alarmType", AlarmType.ONE_TIME_ALARM_PER_DAY.toString())
            putExtra("id", requestCode)
            putExtra("time", time)
        }
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent1, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val repeatInterval: Long = time * 1000
        val triggerTime = (SystemClock.elapsedRealtime() + repeatInterval)
        Log.i("MYTAG", "트리거 타임 : ${triggerTime}")
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)

//        Toast.makeText(context, "Exact periodic Alarm On", Toast.LENGTH_SHORT).show()
    }

//    fun unregisterAlarmOneTime(context: Context, requestCode: Int) {
//        if(!::alarmManager.isInitialized) {
//            makeAlarmManager(context)
//        }
//
//        pendingIntent = PendingIntent.getBroadcast(context, requestCode, Intent(context, AlarmBroadcastReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
//
//        alarmManager.cancel(pendingIntent)
////        Toast.makeText(context, "Exact periodic Alarm Off", Toast.LENGTH_SHORT).show()
//    }


    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun stringDateToDate(date: String): Date {
        return dateFormat.parse("${date}")!!
    }


    fun calculateTime(date: String): Long {
        val date = dateFormat.parse("${date}")
        Log.i("MYTAG", "데이트: ${date}")
        val timeInMillis = date!!.time
        val time = (timeInMillis - System.currentTimeMillis()) / 1000
        Log.i("MYTAG", "${timeInMillis} / ${System.currentTimeMillis() / time}")
        return time
    }

    fun convertDatePlusDayString(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT+09:00")
        val date = inputFormat.parse("${dateString}")

        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.add(Calendar.DAY_OF_YEAR, 1)

        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val formattedDate = outputFormat.format(calendar.time)

        return formattedDate
    }

    fun convertDateMinusDayString(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT+09:00")
        val date = inputFormat.parse("${dateString}")

        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.add(Calendar.DAY_OF_YEAR, -1)

        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val formattedDate = outputFormat.format(calendar.time)

        return formattedDate
    }

    fun convertDateDayString(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT+09:00")
        val date = inputFormat.parse("${dateString}")

        val calendar = Calendar.getInstance()
        calendar.time = date

        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val formattedDate = outputFormat.format(calendar.time)

        return formattedDate
    }
}