package com.example.PermissionAndLogUtilDemo1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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

    interface ResultCallback {
        fun result(enable: Boolean)
    }

    private var resultCallback: ResultCallback? = null

    fun setPermissionResultCallback(resultCallback: ResultCallback) {
        this.resultCallback = resultCallback
    }

    private var commonDialog: CommonDialog? = null

    private val permissionDeniedList = ArrayList<String>()
    private val permissionRationaleList = ArrayList<String>()

    private val permissionAdditionalDeniedList = ArrayList<String>()
    private val permissionAdditionalRationaleList = ArrayList<String>()

    /*
    * 권한
    * */
    private val permissionOfCamera = Manifest.permission.CAMERA
    private val permissionOfNotifications = Manifest.permission.POST_NOTIFICATIONS
    private val permissionOfFindLocation = Manifest.permission.ACCESS_FINE_LOCATION
    private val permissionOfCoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
    private val permissionOfMediaImages = Manifest.permission.READ_MEDIA_IMAGES
    private val permissionOfMediaVideo = Manifest.permission.READ_MEDIA_VIDEO
    private val permissionOfMediaAudio = Manifest.permission.READ_MEDIA_AUDIO
    private val permissionOfExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE
    private val permissionOfBackgroundLocation = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    private val permissionOfPhoneState = Manifest.permission.READ_PHONE_STATE
    private val permissionOfPhoneNumbers = Manifest.permission.READ_PHONE_NUMBERS
    private val permissionOfCallPhone = Manifest.permission.CALL_PHONE
    private val permissionOfWriteExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE




    private val permissionMap: Map<String, String> = mapOf(
        permissionOfCamera to "카메라",
        permissionOfNotifications to "알림",
        permissionOfFindLocation to "위치",
        permissionOfCoarseLocation to "위치",
        permissionOfMediaImages to "사진",
        permissionOfMediaVideo to "동영상",
        permissionOfMediaAudio to "오디오",
        permissionOfExternalStorage to "파일",
        permissionOfBackgroundLocation to "위치(항상허용)",
        permissionOfPhoneState to "전화",
        permissionOfPhoneNumbers to "전화",
        permissionOfCallPhone to "전화",
        permissionOfWriteExternalStorage to "파일쓰기"
    )

    /**
     * 권한 체크 (하나라도 누락되어있으면 false 반환)
     * */
    fun checkAllPermissions(): Boolean {
        val version = Build.VERSION.SDK_INT

        if(version >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(activity, permissionOfCamera) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfNotifications) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfFindLocation) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfCoarseLocation) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfMediaImages) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfMediaVideo) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfMediaAudio) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfPhoneState) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfPhoneNumbers) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfCallPhone) != PackageManager.PERMISSION_GRANTED) { return false }

            // Q 이상
            if(ContextCompat.checkSelfPermission(this.activity.applicationContext, permissionOfBackgroundLocation) != PackageManager.PERMISSION_GRANTED) { return false }
        } else if(version >= Build.VERSION_CODES.Q) {
            if(ContextCompat.checkSelfPermission(activity, permissionOfCamera) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfFindLocation) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfCoarseLocation) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfExternalStorage) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfPhoneState) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfPhoneNumbers) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfCallPhone) != PackageManager.PERMISSION_GRANTED) { return false }

            // Q 이상
            if(ContextCompat.checkSelfPermission(this.activity.applicationContext, permissionOfBackgroundLocation) != PackageManager.PERMISSION_GRANTED) { return false }
            // Tiramisu 이하
            if(ContextCompat.checkSelfPermission(this.activity.applicationContext, permissionOfWriteExternalStorage) != PackageManager.PERMISSION_GRANTED) { return false }
        } else if(version >= Build.VERSION_CODES.O) {
            if(ContextCompat.checkSelfPermission(activity, permissionOfCamera) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfFindLocation) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfCoarseLocation) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfExternalStorage) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfPhoneState) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfPhoneNumbers) != PackageManager.PERMISSION_GRANTED) { return false }
            if(ContextCompat.checkSelfPermission(activity, permissionOfCallPhone) != PackageManager.PERMISSION_GRANTED) { return false }

            // Tiramisu 이하
            if(ContextCompat.checkSelfPermission(this.activity.applicationContext, permissionOfWriteExternalStorage) != PackageManager.PERMISSION_GRANTED) { return false }
        } else {

        }

        return true
    }

    /**
     * 권한 요청
     * */
    fun requestAllPermissions() {
        val version = Build.VERSION.SDK_INT

        if(version >= Build.VERSION_CODES.TIRAMISU) {
            requestAllPermissionsLauncher.launch(arrayOf(
                permissionOfCamera,
                permissionOfNotifications,
                permissionOfFindLocation,
                permissionOfCoarseLocation,
                permissionOfMediaImages,
                permissionOfMediaVideo,
                permissionOfMediaAudio,
                permissionOfPhoneState,
                permissionOfPhoneNumbers,
                permissionOfCallPhone
            ))
        } else if(version >= Build.VERSION_CODES.Q) {
            requestAllPermissionsLauncher.launch(arrayOf(
                permissionOfCamera,
                permissionOfFindLocation,
                permissionOfCoarseLocation,
                permissionOfExternalStorage,
                permissionOfPhoneState,
                permissionOfPhoneNumbers,
                permissionOfCallPhone,
                permissionOfWriteExternalStorage
            ))
        } else if(version >= Build.VERSION_CODES.O) {
            requestAllPermissionsLauncher.launch(arrayOf(
                permissionOfCamera,
                permissionOfFindLocation,
                permissionOfCoarseLocation,
                permissionOfExternalStorage,
                permissionOfPhoneState,
                permissionOfPhoneNumbers,
                permissionOfCallPhone,
                permissionOfWriteExternalStorage
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
                permissionDeniedList.add(p.key) // 거부된 권한 추가
                // 권한 재요청이 가능 여부
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, p.key)) {
                    permissionRationaleList.add(p.key)
                }
            }
        }

        if(permissionDeniedList.size > 0) {
            if(permissionRationaleList.size > 0) {
                // 권한 재요청 가능
                showDeniedPermissionAlert(permissionRationaleList)
            } else {
                // 권한 재요청 불가능하므로 설정으로 이동
                showMoveToPermissionSettingAlert(permissionDeniedList)
            }
        } else {
            // 요청한 모든 권한 허용되었음.
            // 선행 권한 허용이 필요한 권한이 있을 경우, 여기에서 요청
            // 위치 권한 허용되었으면 진행 (Q버전 이상)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // ACEESS_FINE_LOCATION, ACCESS_COARSE_LOCATION 권한이 허용되었는지 확인
                if(ContextCompat.checkSelfPermission(activity, permissionOfFindLocation) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activity, permissionOfCoarseLocation) == PackageManager.PERMISSION_GRANTED) {
                    // ACCESS_BACKGROUND_LOCATION 권한이 허용되었는지 확인
                    if(ContextCompat.checkSelfPermission(this.activity.applicationContext, permissionOfBackgroundLocation) == PackageManager.PERMISSION_GRANTED) {
                        resultCallback?.result(true)
                    } else {
                        showRequestAdditionalPermissionAlert(arrayOf(permissionOfBackgroundLocation))
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
                // 권한 재요청이 가능 여부
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, p.key)) {
                    permissionAdditionalRationaleList.add(p.key)
                }
            }
        }

        if(permissionAdditionalDeniedList.size > 0) {
            if(permissionAdditionalRationaleList.size > 0) {
                // 권한 재요청 가능
                showDeniedPermissionAlert(permissionAdditionalRationaleList)
            } else {
                // 권한 재요청 불가능하므로 설정으로 이동
                moveToPermissionSettingPage()
            }
        } else {
            // 요청한 모든 권한 허용되었음.
            resultCallback?.result(true)
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
            try {
                val permissionNickname = permissionMap.getValue(deniedList.get(i))
                if(!str.contains("${permissionNickname}", ignoreCase = true)) {
                    str.append("[${permissionNickname}]\n")
                }
            } catch (e: Exception) {
                continue
            }
        }
        str.append("\n권한 허용 후 사용해주세요.")

        if(commonDialog != null) {
            commonDialog?.dialogCancel()
            commonDialog = null
        }
        commonDialog = CommonDialog(activity).apply {
            setDialogCancelable(false)
            setContents(str.toString())
            setPositiveText("확인")
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setClickResultListener(object: CommonDialog.ClickResultCallback {
                override fun clickResult(agree: Boolean?) {
                    if(agree == null) {
                        return
                    } else if(agree) {
                        requestAllPermissions()
                    }
                }
            })
            show()
        }
    }

    /**
     * 권한 설정으로 이동 알림창
     * */
    private fun showMoveToPermissionSettingAlert(deniedList: ArrayList<String>) {
        var str = StringBuilder()
        str.append("다음 권한을 허용해주세요.\n\n")
        for(i in 0 until deniedList.size) {
            try {
                val permissionNickname = permissionMap.getValue(deniedList.get(i))
                if(!str.contains("${permissionNickname}", ignoreCase = true)) {
                    str.append("[${permissionNickname}]\n")
                }
            } catch (e: Exception) {
                continue
            }
        }
        str.append("\n권한 허용 후 사용해주세요.\\")

        if(commonDialog != null) {
            commonDialog?.dialogCancel()
            commonDialog = null
        }
        commonDialog = CommonDialog(activity).apply {
            setDialogCancelable(false)
            setContents(str.toString())
            setPositiveText("설정으로 이동")
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setClickResultListener(object: CommonDialog.ClickResultCallback {
                override fun clickResult(agree: Boolean?) {
                    if(agree == null) {
                        return
                    } else if(agree) {
                        moveToPermissionSettingPage()
                    }
                }
            })
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
            try {
                val permissionNickname = permissionMap.getValue(additionalPermissionArray.get(i))
                if(!str.contains("${permissionNickname}", ignoreCase = true)) {
                    str.append("[${permissionNickname}]\n")
                }
            } catch (e: Exception) {
                continue
            }
        }
        str.append("\n권한 허용 후 사용해주세요.")

        if(commonDialog != null) {
            commonDialog?.dialogCancel()
            commonDialog = null
        }
        commonDialog = CommonDialog(activity).apply {
            setDialogCancelable(false)
            setContents(str.toString())
            setPositiveText("확인")
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setClickResultListener(object: CommonDialog.ClickResultCallback {
                override fun clickResult(agree: Boolean?) {
                    if(agree == null) {
                        return
                    } else if(agree) {
                        requestPermissionForBackgroundLocationLauncher.launch(additionalPermissionArray)
                    }
                }
            })
            show()
        }
    }
}