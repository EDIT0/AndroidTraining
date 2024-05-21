package com.example.PermissionAndLogUtilDemo1

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class BatteryOptimizationManager(
    private val activity: AppCompatActivity
) {

    interface ResultCallback {
        fun result(enable: Boolean)
    }

    private var resultCallback: ResultCallback? = null

    private var commonDialog: CommonDialog? = null

    fun setBatteryOptimizationResultCallback(resultCallback: ResultCallback) {
        this.resultCallback = resultCallback
    }

    private val requestIgnoreBatteryOptimizationLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        LogUtil.d_dev("배터리 최적화 기능 중지 결과: ${result.resultCode}")

        if(checkIgnoringBatteryOptimizations()) {
            // 사용자 승인
            LogUtil.d_dev("배터리 최적화 기능 중지 승인")
            this.resultCallback?.result(true)
        } else {
            // 사용자 거부
            LogUtil.d_dev("배터리 최적화 기능 중지 거부")
            this.resultCallback?.result(false)
        }
    }

    fun requestIgnoreBatteryOptimizations() {
        if(commonDialog != null) {
            commonDialog?.dialogCancel()
            commonDialog = null
        }
        commonDialog = CommonDialog(activity).apply {
            setDialogCancelable(false)
            setContents("백그라운드 실행을 위해 배터리 최적화 중지 권한을 허용해주세요.")
            setPositiveText("확인")
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setClickResultListener(object: CommonDialog.ClickResultCallback {
                override fun clickResult(agree: Boolean?) {
                    if(agree == null) {
                        return
                    } else if(agree) {
                        try {
                            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                                data = Uri.parse("package:${activity.packageName}")
                            }
                            requestIgnoreBatteryOptimizationLauncher.launch(intent)
                            LogUtil.d_dev("배터리 최적화 기능 중지 요청")
                        } catch (e: Exception) {
                            LogUtil.e_dev("배터리 최적화 기능 중지 요청 실패")
                        }
                    }
                }
            })
            show()
        }
    }

    fun checkIgnoringBatteryOptimizations(): Boolean {
        val powerManager = activity.applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        val isIgnoring = powerManager.isIgnoringBatteryOptimizations(activity.applicationContext.packageName)
        LogUtil.d_dev("배터리 최적화 중지 여부: $isIgnoring")
        return isIgnoring
    }

}