package com.edit.widgetexample1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast


/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        // 일반적인 브로드캐스팅 receiver, 위젯이 붙었을 때, 위젯이 삭제되었을 때 호출
        Toast.makeText(context, "onReceive()", Toast.LENGTH_SHORT).show()

        Log.d("MYTAG", "onReceive() ${intent?.action}")

        var action = intent?.action
        if (action == MY_ACTION) {
            // TODO
        }
    }
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            Log.d("MYTAG", "appWidgetId: ${appWidgetId}")
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Log.d("MYTAG", "onEnabled()")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d("MYTAG", "onDisabled()")
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
//    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
//    views.setTextViewText(R.id.imageButton_my, widgetText)
    views.setOnClickPendingIntent(R.id.ibHome, buildHomeIntent(context))
//    views.setTextViewText(R.id.btnNaver, "zzzzzzzz")
    views.setOnClickPendingIntent(R.id.btnNaver, buildURIIntent(context))
    views.setOnClickPendingIntent(R.id.btnBroadcast, setMyAction(context))
    views.setOnClickPendingIntent(R.id.btnSendData, buildSendDataIntent(context))

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)

}

private fun buildURIIntent(context: Context?): PendingIntent {
//    val intent = Intent(context, MainActivity::class.java)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com"))
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
}
// PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

private fun buildHomeIntent(context: Context?): PendingIntent {
    val intent = Intent(context, FirstActivity::class.java)
//    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com"))
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
}

private fun buildSendDataIntent(context: Context?): PendingIntent {
    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra("ACTION", "FromHomeToMain")
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
}

private val MY_ACTION = "com.edit.widgetexample1.action.MY_ACTION"

private fun setMyAction(context: Context?): PendingIntent {
    val intent = Intent(MY_ACTION)
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
}