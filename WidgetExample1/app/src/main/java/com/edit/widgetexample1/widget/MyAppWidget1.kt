package com.edit.widgetexample1.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.edit.widgetexample1.R
import com.edit.widgetexample1.Util
import com.edit.widgetexample1.model.UserInfo

class MyAppWidget1: AppWidgetProvider() {

    companion object {
        const val ACTION_OPEN_DEEP_LINK = "com.edit.widgetexample1.ACTION_OPEN_DEEP_LINK"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        Log.d("MYTAG", "${intent?.action}")

        if (intent?.action == ACTION_OPEN_DEEP_LINK) {
            val randomId = Util.getRandomNumber(0, 50).toString()
            val randomNumber = Util.getRandomNumber(50, 100).toString()
            val userInfo = UserInfo(name = "Umm James", number = randomNumber)

            val deepLinkUri = Uri.parse("myapp://widget")
            val activityIntent = Intent(Intent.ACTION_VIEW, deepLinkUri)
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            activityIntent.putExtra("item_id", randomId)
            activityIntent.putExtra("item_boolean", randomNumber.toInt() > 75)
            activityIntent.putExtra("item_user_data", userInfo)

            context?.startActivity(activityIntent)
        }
    }

    override fun onEnabled(context: Context) {
        Log.d("MYTAG", "onEnabled()")
    }

    override fun onDisabled(context: Context) {
        Log.d("MYTAG", "onDisabled()")
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        if(context != null && appWidgetManager != null && appWidgetIds != null) {
            for(i in 0 until appWidgetIds.size) {
                Log.d("MYTAG", "appWidgetId: ${appWidgetIds[i]}")
                updateMyAppWidget1(context, appWidgetManager, appWidgetIds[i])
            }
        }
    }

    private fun updateMyAppWidget1(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.my_app_widget_1)

        views.setOnClickPendingIntent(R.id.btnRandomNumber, deepLinkBroadcastPendingIntent(context))

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    /**
     * 위젯 onReceive() 브래드캐스트 호출
     *
     * @param context
     * @return
     */
    private fun deepLinkBroadcastPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MyAppWidget1::class.java)
        intent.action = ACTION_OPEN_DEEP_LINK

        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return pendingIntent
    }
}