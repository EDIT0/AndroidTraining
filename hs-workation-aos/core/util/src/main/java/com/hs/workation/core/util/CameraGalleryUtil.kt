package com.hs.workation.core.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraGalleryUtil {

    /**
     * Uri To Bitmap
     * 이미지 bitmap 생성하여 리턴
     *
     * @param activity
     * @param imageUri
     * @return
     */
    fun getBitmapFromUri(activity: Activity, imageUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) { // Api 버전별 이미지 처리
                val source: ImageDecoder.Source = ImageDecoder.createSource(activity.contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(activity.contentResolver, imageUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    /**
     * 이미지 저장
     * 내부저장소에 원본 이미지 복사하여 저장
     *
     * @param context
     * @param path
     * @param fileName
     * @param bitmap
     * @return 복사된 이미지 uri
     */
    fun saveImageInInternalStorage(
        context: Context,
        path: String,
        fileName: String,
        bitmap: Bitmap
    ): Uri? {
        val dir = File(context.filesDir, path)

        if (!dir.exists()) {
            dir.mkdirs()
        }

        val file = File(dir, fileName)
        val fileOutputStream = FileOutputStream(file)

        return try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            LogUtil.i_dev("Saved image at ${file.absolutePath}")

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider", // Manifest의 Provider의 authorities와 일치해야 함
                file
            )
        } catch (e: Exception) {
            null
        } finally {
            fileOutputStream.close()
        }
    }

    /**
     * Uri로 내부저장소 파일 삭제
     *
     * @param context
     * @param uri
     * @return
     */
    fun deleteFileByUri(
        context: Context,
        uri: Uri
    ): Boolean {
        return try {
            val file = File(uri.path ?: return false)
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Path로 내부저장소 파일 삭제
     *
     * @param context
     * @param path
     * @param fileName
     * @return
     */
    fun deleteFile(
        context: Context,
        path: String,
        fileName: String
    ): Boolean {
        val file = File(context.filesDir, "${path}/${fileName}")
        return file.delete()
    }
}