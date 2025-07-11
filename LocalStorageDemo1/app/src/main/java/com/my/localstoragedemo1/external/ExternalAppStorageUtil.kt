package com.my.localstoragedemo1.external

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

object ExternalAppStorageUtil {

    fun saveImage(
        context: Context,
        path: String,
        fileName: String,
        bitmap: Bitmap
    ): Uri? {
        val dir = File(context.getExternalFilesDir(null), path)

        if (!dir.exists()) {
            dir.mkdirs()
        }

        val file = File(dir, fileName)
        val fileOutputStream = FileOutputStream(file)

        return try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            Log.i("MYTAG", "Saved image at ${file.absolutePath}")
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: Exception) {
            null
        } finally {
            fileOutputStream.close()
        }
    }

    fun saveText(
        context: Context,
        path: String,
        fileName: String,
        text: String
    ): Boolean {
        val dir = File(context.getExternalFilesDir(null), path)

        if (!dir.exists()) {
            dir.mkdirs()
        }

        val file = File(dir, fileName)
        return try {
            file.writeText(text)
            Log.i("MYTAG", "Save text ${dir}/${fileName}")
            true
        } catch (e: Exception) {
            Log.e("MYTAG", "${e}")
            false
        }
    }

    fun loadImage(
        context: Context,
        path: String,
        fileName: String
    ): Bitmap? {
        val file = File(context.getExternalFilesDir(null), "$path/$fileName")
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }

    fun loadText(
        context: Context,
        path: String,
        fileName: String
    ): String? {
        val file = File(context.getExternalFilesDir(null), "$path/$fileName")
        return if (file.exists()) file.readText() else null
    }

    fun deleteFileByUri(
        context: Context,
        uri: Uri
    ): Boolean {
        return try {
            val file = File(uri.path ?: return false)
            file.delete()
        } catch (e: Exception) {
            Log.e("MYTAG", "${e}")
            false
        }
    }

    fun deleteFile(
        context: Context,
        path: String,
        fileName: String
    ): Boolean {
        val file = File(context.getExternalFilesDir(null), "$path/$fileName")
        return file.delete()
    }

    fun getFileList(
        context: Context,
        path: String
    ): List<String> {
        val folder = File(context.getExternalFilesDir(null), path)
        return if (folder.exists() && folder.isDirectory) {
            folder.list()?.toList() ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun getBitmapFromUri(
        activity: Activity,
        photoUri: Uri
    ): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT > 27) {
                val source: ImageDecoder.Source = ImageDecoder.createSource(activity.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(activity.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            Log.e("MYTAG", "${e}")
            null
        }
    }

    fun absolutelyPath(
        activity: Activity,
        path: Uri
    ): String? {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = activity.contentResolver.query(path, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        return index?.let { c.getString(it) }
    }

    fun getAbsolutePath(
        context: Context,
        path: String,
        uri: Uri
    ): String? {
        return try {
            val fileName = getFileNameFromUri(context, uri) ?: return null
            val dir = File(context.getExternalFilesDir(null), path)
            val file = File(dir, fileName)
            if (file.exists()) file.absolutePath else null
        } catch (e: Exception) {
            Log.e("MYTAG", "${e}")
            null
        }
    }

    fun getFileNameFromUri(
        context: Context,
        uri: Uri
    ): String? {
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