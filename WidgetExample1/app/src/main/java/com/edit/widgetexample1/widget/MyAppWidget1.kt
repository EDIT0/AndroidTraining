package com.edit.widgetexample1.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.edit.widgetexample1.DeepLinkActivity.Companion.PATH_INTENT_TYPE
import com.edit.widgetexample1.DeepLinkActivity.Companion.PATH_URI_TYPE
import com.edit.widgetexample1.DeepLinkActivity.Companion.WIDGET_HOST
import com.edit.widgetexample1.DeepLinkActivity.Companion.WIDGET_SCHEME
import com.edit.widgetexample1.R
import com.edit.widgetexample1.Util
import com.edit.widgetexample1.model.UserInfo

class MyAppWidget1: AppWidgetProvider() {

    companion object {
        const val ACTION_OPEN_DEEP_LINK_URI = "com.edit.widgetexample1.ACTION_OPEN_DEEP_LINK_URI"
        const val ACTION_OPEN_DEEP_LINK_INTENT = "com.edit.widgetexample1.ACTION_OPEN_DEEP_LINK_INTENT"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        Log.d("MYTAG", "Broadcast action in widget: ${intent?.action}")

        if (intent?.action == ACTION_OPEN_DEEP_LINK_URI) {
            val randomId = Util.getRandomNumber(0, 50).toString()
            val randomNumber = Util.getRandomNumber(50, 100).toString()
            val userInfo = UserInfo(name = "Umm James", number = randomNumber)

            val uriBuilder = Uri.parse("${WIDGET_SCHEME}://${WIDGET_HOST}").buildUpon()
                .appendPath(PATH_URI_TYPE)
                .appendQueryParameter("item_id", randomId)
                .appendQueryParameter("item_boolean", (randomNumber.toInt() > 75).toString())
                .appendQueryParameter("item_user_name", userInfo.name)
                .appendQueryParameter("item_user_data", userInfo.number)
            val deepLinkUri = uriBuilder.build()

            val activityIntent = Intent(Intent.ACTION_VIEW, deepLinkUri)
            // 새로운 Activity 시작
//            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            // 현재 Activity가 열려있으면 재사용
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

            context?.startActivity(activityIntent)
        } else if(intent?.action == ACTION_OPEN_DEEP_LINK_INTENT) {
            val randomId = Util.getRandomNumber(0, 50).toString()
            val randomNumber = Util.getRandomNumber(50, 100).toString()
            val userInfo = UserInfo(name = "Umm James", number = randomNumber)

            val uriBuilder = Uri.parse("${WIDGET_SCHEME}://${WIDGET_HOST}").buildUpon()
                .appendPath(PATH_INTENT_TYPE)
            val deepLinkUri = uriBuilder.build()

            val activityIntent = Intent(Intent.ACTION_VIEW, deepLinkUri)
            // 새로운 Activity 시작
//            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            // 현재 Activity가 열려있으면 재사용
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

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

        views.setOnClickPendingIntent(R.id.btnDeepLinkUri, deepLinkUriBroadcastPendingIntent(context))
        views.setOnClickPendingIntent(R.id.btnDeepLinkIntent, deepLinkIntentBroadcastPendingIntent(context))

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    /**
     * 위젯 onReceive() 브래드캐스트 호출
     *
     * @param context
     * @return
     */
    private fun deepLinkUriBroadcastPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MyAppWidget1::class.java)
        intent.action = ACTION_OPEN_DEEP_LINK_URI

        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return pendingIntent
    }

    private fun deepLinkIntentBroadcastPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MyAppWidget1::class.java)
        intent.action = ACTION_OPEN_DEEP_LINK_INTENT

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