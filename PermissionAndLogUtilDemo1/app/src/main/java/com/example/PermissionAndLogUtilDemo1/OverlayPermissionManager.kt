package com.example.PermissionAndLogUtilDemo1

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class OverlayPermissionManager(
    private val activity: AppCompatActivity
) {

    interface ResultCallback {
        fun result(enable: Boolean)
    }

    private var resultCallback: ResultCallback? = null

    fun setOverlayPermissionResultCallback(resultCallback: ResultCallback) {
        this.resultCallback = resultCallback
    }

    private var commonDialog: CommonDialog? = null

    private val manageOverlayPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (checkOverlayPermission()) {
            // 사용자 '다른 앱 위에 그리기' 권한 승인
            LogUtil.d_dev("다른 앱 위에 표시 허용")
        } else {
            // 사용자가 권한 요청 거부
            LogUtil.d_dev("다른 앱 위에 표시 거부")
        }
    }

    fun requestOverlayPermission() {
        if(commonDialog != null) {
            commonDialog?.dialogCancel()
            commonDialog = null
        }
        commonDialog = CommonDialog(activity).apply {
            setDialogCancelable(false)
            setContents("백그라운드 실행을 위해 다른 앱 위에 표시를 허용해주세요.")
            setPositiveText("확인")
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setClickResultListener(object: CommonDialog.ClickResultCallback {
                override fun clickResult(agree: Boolean?) {
                    if(agree == null) {
                        return
                    } else if(agree) {
                        try {
                            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${activity.packageName}"))
                            manageOverlayPermissionLauncher.launch(intent)
                        } catch (e: Exception) {
                            LogUtil.e_dev("다른 앱 위에 표시 요청 실패")
                        }
                    }
                }
            })
            show()
        }
    }

    fun checkOverlayPermission(): Boolean {
        return Settings.canDrawOverlays(activity)
    }
}