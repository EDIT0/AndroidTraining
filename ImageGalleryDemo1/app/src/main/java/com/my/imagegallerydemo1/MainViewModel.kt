package com.my.imagegallerydemo1

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    enum class MyEvent {
        MoveToSettings, RetryRequest, AllPass
    }

    private lateinit var pm: PermissionManager
    private lateinit var gm: GalleryManager

    private val _event = MutableSharedFlow<MyEvent>()
    val event = _event.asSharedFlow()

    /**
     * 권한 매니저 초기화
     *
     * @param activity
     */
    fun initPermissionManager(activity: AppCompatActivity) {
        pm = PermissionManager(
            activity = activity,
            result = { isRetryAvailable: Boolean, rejectedPermissions: ArrayList<String> ->
                LogUtil.i_dev("권한 요청 결과 isRetryAvailable: ${isRetryAvailable}")
                LogUtil.i_dev("권한 요청 결과 거절 리스트: ${rejectedPermissions.toArray().contentToString()}")

                if(!isRetryAvailable) {
                    // 셋팅페이지
                    viewModelScope.launch {
                        _event.emit(MyEvent.MoveToSettings)
                    }
                } else if(isRetryAvailable && rejectedPermissions.isNotEmpty()) {
                    // 재요청
                    viewModelScope.launch {
                        _event.emit(MyEvent.RetryRequest)
                    }
                } else {
                    // 통과
                    viewModelScope.launch {
                        _event.emit(MyEvent.AllPass)
                    }
                }
            }
        )
    }

    /**
     * 권한 요청
     * 리스트에 필요한 권한 넣기
     */
    fun requestPermissions() {
        pm.requestPermissions(
            arrayListOf(
                pm.permissionOfPostNotifications,
                pm.permissionOfReadMediaImages,
//                pm.permissionOfReadMediaVisualUserSelected
            )
        )
    }

    /**
     * 설정 페이지로 이동
     *
     */
    fun navigateToPermissionSettingPage() {
        pm.navigateToPermissionSettingPage()
    }


    private fun initGalleryManager(activity: Activity) {
        gm = GalleryManager(
            activity = activity
        )
    }

    // 갤러리 전용
    // 한장
    private var _galleryPhotoURI: Uri? = null
    val galleryPhotoURI get() = _galleryPhotoURI
    fun setGalleryPhotoURI(uri: Uri) {
        _galleryPhotoURI = uri
    }
    // 여러장
    val galleryPhotoURIArrayList = ArrayList<Uri>()

    // 카메라 전용
    private var _photoURI: Uri? = null
    val photoURI get() = _photoURI
    fun setPhotoURI(uri: Uri) {
        _photoURI = uri
    }

    /**
     * 카메라 열기 요청
     *
     * @param activity
     */
    fun openCamera(activity: Activity) {
        if(!::gm.isInitialized) {
            initGalleryManager(activity)
        }

        val contentValues = ContentValues()
        _photoURI = activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        _photoURI?.let { gm.openCamera(it) }
    }

    /**
     * 이미지 파일 삭제
     *
     * @param imagePath 없으면 null로 넣기
     * @param imagePathArray 없으면 arrayListOf()로 넣기
     */
    fun deleteCameraImage(imagePath: String, imagePathArray: ArrayList<String>) {
        gm.initFileSetting(imagePath, imagePathArray)
    }

    /**
     * 갤러리 열기 (한장)
     *
     * @param activity
     */
    fun openGallery(activity: Activity) {
        if(!::gm.isInitialized) {
            initGalleryManager(activity)
        }

        gm.openGallery()
    }

    /**
     * 갤러리 열기 (여러장)
     *
     * @param activity
     */
    fun openMultiGallery(activity: Activity) {
        if(!::gm.isInitialized) {
            initGalleryManager(activity)
        }

        gm.openMultiGallery()
    }
}