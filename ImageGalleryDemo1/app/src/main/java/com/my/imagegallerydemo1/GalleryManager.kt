package com.my.imagegallerydemo1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import java.io.File

class GalleryManager(
    val activity: Activity
) {

    companion object {
        const val FLAG_REQ_CAMERA = 200
        const val FLAG_REQ_GALLERY = 100
        const val FLAG_REQ_MULTI_GALLERY = 300
    }

    /**
     * 카메라 열기
     *
     * @param photoUri 카메라로 찍은 이미지의 uri명
     */
    fun openCamera(photoUri: Uri) {
        // 카메라 인텐트 생성
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        activity.startActivityForResult(
            cameraIntent,
            FLAG_REQ_CAMERA
        )
    }

    /**
     * 갤러리 열기 (한장)
     */
    fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(intent, FLAG_REQ_GALLERY)
    }

    /**
     * 갤러리 열기 (여러장)
     */
    fun openMultiGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        activity.startActivityForResult(Intent.createChooser(intent,"사용하실 사진 앱을 선택해주세요."), FLAG_REQ_MULTI_GALLERY)
    }

    /**
     * 파일 삭제
     *
     * @param imagePath 없으면 null로 넣기
     * @param imagePathArray 없으면 arrayListOf()로 넣기
     */
    fun initFileSetting(imagePath: String?, imagePathArray: ArrayList<String>) {
        // 성공하든 안하든 이미지 삭제
        var deleteFile: File
        if(imagePath != null) {
            deleteFile = File(imagePath)
            deleteFile.delete()
            LogUtil.d_dev("한장 삭제 ${imagePath}")
        }

        for(i in 0 until imagePathArray.size) {
            LogUtil.d_dev("여러장 삭제 ${i} / ${imagePathArray[i]}")
            deleteFile = File(imagePathArray.get(i))
            deleteFile.delete()
        }
    }
}