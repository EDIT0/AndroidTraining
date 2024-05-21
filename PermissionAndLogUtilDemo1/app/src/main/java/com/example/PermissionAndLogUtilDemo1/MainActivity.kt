package com.example.PermissionAndLogUtilDemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val permissionManager = PermissionManager(this)
    private val batteryOptimizationManager = BatteryOptimizationManager(this)
    private val overlayPermissionManager = OverlayPermissionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        permissionManager.setPermissionResultCallback(object : PermissionManager.ResultCallback {
            override fun result(enable: Boolean) {
                if(!enable) {

                } else {
//                    if(!batteryOptimizationManager.checkIgnoringBatteryOptimizations()) {
//                        batteryOptimizationManager.requestIgnoreBatteryOptimizations()
//                    }
                }
                // 위에 주석 풀어도 잘 동작하지만, 모든 권한이 승인 되었을 때 동작할 곳을 한 곳으로 줄이기 위하여 onStart() 호출
                onStart()
            }
        })

        batteryOptimizationManager.setBatteryOptimizationResultCallback(object : BatteryOptimizationManager.ResultCallback {
            override fun result(enable: Boolean) {
                if(!enable) {
                    // 사용자가 거부
//                    batteryOptimizationManager.requestIgnoreBatteryOptimizations()
                } else {
//                    if(!overlayPermissionManager.checkOverlayPermission()) {
//                        overlayPermissionManager.requestOverlayPermission()
//                    } else {
//                        // 모든 권한 승인되었음. (위험, 배터리 최적화 중지, 다른 앱 위에 표시)
//                        LogUtil.i_dev("모든 권한 승인되었음. (위험, 배터리 최적화 중지, 다른 앱 위에 표시)")
//                    }
                }
                // 위에 주석 풀어도 잘 동작하지만, 모든 권한이 승인 되었을 때 동작할 곳을 한 곳으로 줄이기 위하여 onStart() 호출
                onStart()
            }
        })

        overlayPermissionManager.setOverlayPermissionResultCallback(object : OverlayPermissionManager.ResultCallback {
            override fun result(enable: Boolean) {
                if(!enable) {

                } else {

                }
                // 위에 주석 풀어도 잘 동작하지만, 모든 권한이 승인 되었을 때 동작할 곳을 한 곳으로 줄이기 위하여 onStart() 호출
                onStart()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if(!permissionManager.checkAllPermissions()) {
            permissionManager.requestAllPermissions()
        } else {
            if(!batteryOptimizationManager.checkIgnoringBatteryOptimizations()) {
                batteryOptimizationManager.requestIgnoreBatteryOptimizations()
            } else {
                if(!overlayPermissionManager.checkOverlayPermission()) {
                    overlayPermissionManager.requestOverlayPermission()
                } else {
                    // 모든 권한 승인되었음. (위험, 배터리 최적화 중지, 다른 앱 위에 표시)
                    LogUtil.i_dev("모든 권한 승인되었음. (위험, 배터리 최적화 중지, 다른 앱 위에 표시)")
//                    permissionCallback?.success()
                }
            }
        }
    }
}