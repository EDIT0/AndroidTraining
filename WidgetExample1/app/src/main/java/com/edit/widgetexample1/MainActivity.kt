package com.edit.widgetexample1

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.edit.widgetexample1.databinding.ActivityMainBinding
import com.edit.widgetexample1.widget.NewAppWidget

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fromWidgetToMain = intent.extras?.getString("ACTION", null)
        if(fromWidgetToMain != null) {
            binding.tvText.text = fromWidgetToMain
        }

        findViewById<TextView>(R.id.tvText).setOnClickListener {
            binding.tvText.text = "Init"
            updateWidgetData(this, "Init")
            updateDataAndNotifyWidget(this, "Init")
        }

        binding.btnDeepLink.setOnClickListener {
            val intent = Intent(this@MainActivity, DeepLinkActivity::class.java)
            val dataBundle = Bundle().apply {
                putString("key1", "value1")
                putInt("key2", 123)
            }
            intent.putExtras(dataBundle)
            launcher.launch(intent)
        }
    }

    fun updateWidgetData(context: Context, newData: String) {
        // 위젯 업데이트 준비
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetComponent = ComponentName(context, NewAppWidget::class.java)

        // 현재 활성화된 모든 위젯 ID 가져오기
        val appWidgetIds = appWidgetManager.getAppWidgetIds(widgetComponent)

        // 위젯에 표시할 RemoteViews 생성
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)

        // 새로운 데이터로 위젯 UI 업데이트
        views.setTextViewText(R.id.btnSendData, newData)
        // 필요한 다른 UI 업데이트...

        // 모든 위젯 인스턴스 업데이트
        appWidgetManager.updateAppWidget(widgetComponent, views)

        // 또는 특정 위젯만 업데이트:
        // for (widgetId in appWidgetIds) {
        //     appWidgetManager.updateAppWidget(widgetId, views)
        // }
    }

    fun updateDataAndNotifyWidget(context: Context, newData: String) {
        // 위젯에 업데이트 알림
        val intent = Intent("com.edit.widgetexample1.action.MY_ACTION")
        // 필요시 데이터 추가 (단, 크기 제한 있음)
        intent.putExtra("SOME_KEY", "SOME_VALUE")
        context.sendBroadcast(intent)
    }
}