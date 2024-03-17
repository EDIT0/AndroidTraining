package com.example.PermissionAndLogUtilDemo1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.StringBuilder

class PermissionManager(
    private val activity: AppCompatActivity
) {

    private val permissionDeniedList = ArrayList<String>()
    private val permissionRationaleList = ArrayList<String>()

    private val permissionAdditionalDeniedList = ArrayList<String>()
    private val permissionAdditionalRationaleList = ArrayList<String>()

    private val permissionMap: Map<String, String> = mapOf(
        Manifest.permission.CAMERA to "카메라",
        Manifest.permission.POST_NOTIFICATIONS to "알림",
        Manifest.permission.ACCESS_FINE_LOCATION to "위치",
        Manifest.permission.ACCESS_COARSE_LOCATION to "위치",
        Manifest.permission.READ_MEDIA_IMAGES to "사진",
        Manifest.permission.READ_MEDIA_VIDEO to "동영상",
        Manifest.permission.READ_MEDIA_AUDIO to "오디오",
        Manifest.permission.READ_EXTERNAL_STORAGE to "파일",
        Manifest.permission.ACCESS_BACKGROUND_LOCATION to "위치(항상허용)"
    )

    fun requestAllPermissions() {
        val version = Build.VERSION.SDK_INT

        if(version >= Build.VERSION_CODES.TIRAMISU) {
            requestAllPermissionsLauncher.launch(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
            ))
        } else if(version >= Build.VERSION_CODES.Q) {
            requestAllPermissionsLauncher.launch(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ))
        } else if(version >= Build.VERSION_CODES.O) {
            requestAllPermissionsLauncher.launch(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ))
        } else {

        }
    }

    fun requestPermission(permissionArray: Array<String>) {
        requestAllPermissionsLauncher.launch(permissionArray)
    }

    /**
     * 설정된 모든 권한 요청
     * */
    private val requestAllPermissionsLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionDeniedList.clear()
        permissionRationaleList.clear()

        for(p in permissions.entries) {
            if(!p.value) {
                permissionDeniedList.add(p.key)
                LogUtil.d_dev("거부된 권한 넣기: ${p.key}")
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, p.key)) {
                    permissionRationaleList.add(p.key)
                    LogUtil.d_dev("Rational 추가: ${p.key}")
                }
            }
        }

        if(permissionDeniedList.size > 0) {
            if(permissionRationaleList.size > 0) {
                LogUtil.d_dev("Rational 기회 부여?")
                showDeniedPermissionAlert(permissionRationaleList)
            } else {
                LogUtil.d_dev("Rational 설정으로 유도?")
                showMoveToPermissionSettingAlert(permissionDeniedList)
            }
        } else {
            LogUtil.d_dev("요청한 모든 권한 허용. 그러므로 백그라운드 로케이션 권한 요청")
            // 선행 권한 허용이 필요한 권한이 있을 경우, 여기에서 요청
            // 위치 권한 허용되었으면 진행 (Q버전 이상)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // ACEESS_FINE_LOCATION, ACCESS_COARSE_LOCATION 권한이 허용되었는지 확인
                if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // ACCESS_BACKGROUND_LOCATION 권한이 허용되었는지 확인
                    if(ContextCompat.checkSelfPermission(this.activity.applicationContext, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        showRequestAdditionalPermissionAlert(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
                    }
                }
            }
        }
    }

    /**
     * 선행 권한 허용이 필요한 권한 요청
     * */
    private val requestPermissionForBackgroundLocationLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionAdditionalDeniedList.clear()
        permissionAdditionalRationaleList.clear()

        for(p in permissions.entries) {
            if(p.value == false) {
                permissionAdditionalDeniedList.add(p.key)
                LogUtil.d_dev("(추가) 거부된 권한 넣기: ${p.key}")
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, p.key)) {
                    permissionAdditionalRationaleList.add(p.key)
                    LogUtil.d_dev("(추가) Rational 추가: ${p.key}")
                }
            }
        }

        if(permissionAdditionalDeniedList.size > 0) {
            if(permissionAdditionalRationaleList.size > 0) {
                LogUtil.d_dev("(추가) Rational 기회 부여?")
                showDeniedPermissionAlert(permissionAdditionalRationaleList)
            } else {
                LogUtil.d_dev("(추가) Rational 설정으로 유도?")
                showMoveToPermissionSettingAlert(permissionAdditionalDeniedList)
            }
        } else {
            LogUtil.d_dev("(추가) 요청한 모든 권한 허용.")
        }
    }

    /**
     * 권한 설정화면으로 이동
     * */
    private fun moveToPermissionSettingPage() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.packageName))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    /**
     * 거부된 권한 알림창
     * */
    private fun showDeniedPermissionAlert(deniedList: ArrayList<String>) {
        var str = StringBuilder()
        str.append("다음 권한을 허용해주세요.\n\n")
        for(i in 0 until deniedList.size) {
            val permissionNickname = permissionMap.getValue(deniedList.get(i))
            if(!str.contains("${permissionNickname}", ignoreCase = true)) {
                str.append("[${permissionNickname}]\n")
            }
        }
        str.append("\n권한 허용 후 사용해주세요.")
        LogUtil.d_dev("Rational 거부된 애들 String -> ${str}")

        val builder = AlertDialog.Builder(activity)
        builder.apply {
            setMessage("${str}")
            setPositiveButton("확인") { dialog, which ->
                requestAllPermissions()
            }
            setCancelable(false)
            show()
        }
    }

    /**
     * 권한 설정으로 이동 알림창
     * */
    private fun showMoveToPermissionSettingAlert(deniedList: ArrayList<String>) {
        var str = StringBuilder()
        str.append("설정에서 다음 권한을 허용해주세요.\n\n")
        for(i in 0 until deniedList.size) {
            val permissionNickname = permissionMap.getValue(deniedList.get(i))
            if(!str.contains("${permissionNickname}", ignoreCase = true)) {
                str.append("[${permissionNickname}]\n")
            }
        }
        str.append("\n권한 허용 후 사용해주세요.")
        LogUtil.d_dev("Rational 거부된 애들 String -> ${str}")

        val builder = AlertDialog.Builder(activity)
        builder.apply {
            setMessage("${str}")
            setPositiveButton("설정으로 이동") { dialog, which ->
                moveToPermissionSettingPage()
            }
            setCancelable(false)
            show()
        }
    }

    /**
     * 추가 권한 요청 알림창
     * */
    private fun showRequestAdditionalPermissionAlert(additionalPermissionArray: Array<String>) {
        var str = StringBuilder()
        str.append("다음 권한을 허용해주세요.\n\n")
        for(i in 0 until additionalPermissionArray.size) {
            val permissionNickname = permissionMap.getValue(additionalPermissionArray.get(i))
            if(!str.contains("${permissionNickname}", ignoreCase = true)) {
                str.append("[${permissionNickname}]\n")
            }
        }
        str.append("\n권한 허용 후 사용해주세요.")
        LogUtil.d_dev("Rational 거부된 애들 String -> ${str}")

        val builder = AlertDialog.Builder(activity)
        builder.apply {
            setMessage("${str}")
            setPositiveButton("확인") { dialog, which ->
                requestPermissionForBackgroundLocationLauncher.launch(additionalPermissionArray)
            }
            setCancelable(false)
            show()
        }
    }
}