package com.my.localstoragedemo1.internal

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object InternalStorageUtil {

    const val BASE_IMAGE_PATH = "/image"
    const val BASE_TEXT_PATH = "/text"

    /**
     * 이미지 저장
     *
     * @param context
     * @param path
     * @param fileName
     * @param bitmap
     * @return
     */
    fun saveImage(
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
            Log.i("MYTAG", "Saved image at ${file.absolutePath}")

            // FileProvider를 통해 Uri 반환
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider", // Manifest에 등록된 authorities와 일치해야 함
                file
            )
        } catch (e: Exception) {
            null
        } finally {
            fileOutputStream.close()
        }
    }

    /**
     * 텍스트 저장
     *
     * @param context
     * @param path
     * @param fileName
     * @param text
     * @return
     */
    fun saveText(
        context: Context,
        path: String,
        fileName: String,
        text: String
    ): Boolean {
        val dir = File(context.filesDir, path)

        if(!dir.exists()) {
            dir.mkdirs()
        }

        val file = File(dir, fileName)
        try {
            file.writeText(text)
            Log.i("MYTAG", "Save text ${context.filesDir}${path}/${fileName}")
        } catch (e: Exception) {
            return false
        }

        return true
    }

    fun loadImage(
        context: Context,
        path: String,
        fileName: String
    ): Bitmap? {
        val file = File(context.filesDir, "${path}/${fileName}")

        if(file.exists()) {
            return BitmapFactory.decodeFile(file.absolutePath)
        } else {
            return null
        }
    }

    fun loadText(
        context: Context,
        path: String,
        fileName: String
    ): String? {
        val file = File(context.filesDir, "${path}/${fileName}")

        if(file.exists()) {
            return file.readText()
        } else {
            return null
        }
    }

    /**
     * 파일 삭제
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

    /**
     * 파일 리스트 가져오기
     *
     * @param context
     * @param path
     * @return
     */
    fun getFileList(context: Context, path: String): List<String> {
        val folder = File(context.filesDir, path)
        return if (folder.exists() && folder.isDirectory) {
            folder.list()?.toList() ?: emptyList()
        } else {
            emptyList()
        }
    }

    /**
     * Uri To Bitmap
     * 이미지 bitmap 생성하여 리턴
     *
     * @param activity
     * @param photoUri
     * @return
     */
    fun getBitmapFromUri(activity: Activity, photoUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) { // Api 버전별 이미지 처리
                val source: ImageDecoder.Source = ImageDecoder.createSource(activity.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(activity.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    // uri 절대 경로
    fun absolutelyPath(activity: Activity, path: Uri): String? {

        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = activity.contentResolver.query(path, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result: String? = null

        if(index != null) {
            result = c.getString(index)
        }

        return result
    }

    // 절대 경로 반환
    fun getAbsolutePath(context: Context, path: String, uri: Uri): String? {
        return try {
            val fileName = getFileNameFromUri(context, uri) ?: return null
            val dir = File(context.filesDir, path)
            val file = File(dir, fileName)
            if (file.exists()) file.absolutePath else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 파일 이름 얻기
    fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var name: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }
}