package com.my.imagegallerydemo1

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
    // 필수
    val permissionOfPostNotifications: String = Manifest.permission.POST_NOTIFICATIONS

    // 선택
    val permissionOfReadMediaImages = Manifest.permission.READ_MEDIA_IMAGES
//    val permissionOfReadMediaVisualUserSelected = Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED

    /**
     * 필수 권한 리스트 반환
     *
     * @return 필수 권한 리스트
     */
    fun getEssentialPermissions() : ArrayList<String> {
        val version = Build.VERSION.SDK_INT
        LogUtil.d_dev("Android version: ${version}")

        if(version >= Build.VERSION_CODES.TIRAMISU) {
            return arrayListOf(
                permissionOfPostNotifications
            )
        } else if(version >= Build.VERSION_CODES.O) {
            return arrayListOf(
                permissionOfPostNotifications
            )
        }

        return arrayListOf()
    }

    /**
     * 권한 설정 확인
     *
     * @param permissions 권한이 설정되었는지 확인하고 싶은 권한 리스트
     * @return 모두 승인: true, 하나라도 거절: false
     */
    fun isGrantedPermissions(permissions: ArrayList<String>): Boolean {
        for(p in permissions) {
            if(ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 권한 요청
     *
     * @param permissions
     */
    fun requestPermissions(permissions: ArrayList<String>) {
        var arr = Array<String>(permissions.size, init = {""})
        arr = Array<String>(permissions.size, init = {""})
        permissions.forEachIndexed { index, s ->
            arr[index] = s
        }
        requestPermissionsLauncher.launch(arr)
    }

    private val requestPermissionsLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var isRetry = true
        val rejectedPermissions = ArrayList<String>()
        for(p in permissions.entries) {
            if(!p.value) {
                LogUtil.d_dev("key: ${p.key} value: ${p.value}")
                rejectedPermissions.add(p.key)

                LogUtil.d_dev("isAvailable retry: ${ActivityCompat.shouldShowRequestPermissionRationale(activity, p.key)}")
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, p.key)) {

                } else {
                    isRetry = false
                }
            }
        }

        result.invoke(isRetry, rejectedPermissions)
    }

    /**
     * 설정 페이지로 이동
     *
     * 권한요청 재시도가 불가능할 경우 이동시키기
     */
    fun navigateToPermissionSettingPage() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.packageName))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }
}