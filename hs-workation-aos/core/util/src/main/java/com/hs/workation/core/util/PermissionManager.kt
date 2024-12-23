package com.hs.workation.core.util

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(
    private val activity: AppCompatActivity,
    private val result: (isRetryAvailable: Boolean, rejectedPermissions: ArrayList<String>) -> Unit
) {

    val permissionOfNotifications = Manifest.permission.POST_NOTIFICATIONS
    val permissionOfFindLocation = Manifest.permission.ACCESS_FINE_LOCATION
    val permissionOfCoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION // ACCESS_FINE_LOCATION 승인 시 함께 사용 가능
    val permissionOfBackgroundLocation = Manifest.permission.ACCESS_BACKGROUND_LOCATION

    private val permissionMap = mapOf(
        permissionOfNotifications to "알림",
        permissionOfFindLocation to "위치",
        permissionOfCoarseLocation to "위치",
        permissionOfBackgroundLocation to "위치(항상허용)"
    )

    /**
     * 필수 권한 리스트
     * */
    fun getEssentialPermissions() : ArrayList<String>{
        val version = Build.VERSION.SDK_INT

        if(version >= Build.VERSION_CODES.TIRAMISU) {
            return arrayListOf(
                permissionOfNotifications,
                permissionOfFindLocation
            )
        } else if(version >= Build.VERSION_CODES.O) {
            return arrayListOf(
                permissionOfFindLocation
            )
        }

        return arrayListOf()
    }

    /**
     * 권한 승인 여부 확인
     * */
    fun isGrantedPermissions(permissions: ArrayList<String>): Boolean {
        for(p in permissions) {
            if(ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private val requestPermissionsLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var isRetry = true
        val rejectedPermissions = ArrayList<String>()
        for(p in permissions.entries) {
            if(!p.value) {
                rejectedPermissions.add(permissionMap[p.key]?:"알수없음")

                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, p.key)) {

                } else {
                    isRetry = false
                }
            }
        }

        result.invoke(isRetry, rejectedPermissions)
    }

    /**
     * 권한 승인 요청
     * */
    fun requestPermissions(permissions: ArrayList<String>) {
        val arr = Array<String>(permissions.size, init = {""})
        permissions.forEachIndexed { index, s ->
            arr[index] = s
        }
        requestPermissionsLauncher.launch(arr)
    }

    /**
     * 설정 페이지로 이동
     * */
    fun navigateToPermissionSettingPage() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.packageName))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

}