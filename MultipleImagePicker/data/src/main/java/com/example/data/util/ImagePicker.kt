package com.example.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.example.domain.model.ImagePickerModel
import java.io.File

class ImagePicker(
    private val context: Context
) {

//    val getAlbumImageListA : () -> MutableList<ImagePickerModel> = {
//        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val projection = arrayOf(
//            INDEX_MEDIA_ID,
//            INDEX_MEDIA_URI,
//            INDEX_ALBUM_NAME,
//            INDEX_DATE_ADDED
//        )
//        val selection =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Images.Media.SIZE + " > 0"
//            else null
//        val sortOrder = "$INDEX_DATE_ADDED DESC"
//        val cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)
//
//        Log.i("MYTAG", "getAlbumImageList()1 ${Thread.currentThread().name} ${cursor}")
//
//        cursor?.let {
//            while(cursor.moveToNext()) {
//                val mediaPath = cursor.getString(cursor.getColumnIndex(INDEX_MEDIA_URI))
////                Log.i("MYTAG", "getAlbumImageList() ${Thread.currentThread().name} ${mediaPath}")
//                imageItemList.add(
//                    ImagePickerModel(Uri.fromFile(File(mediaPath)), false)
//                )
//            }
//        }
//
//        cursor?.close()
//        Log.i("MYTAG", "getAlbumImageList()2 ${Thread.currentThread().name} ${imageItemList.size}")
//        imageItemList
//    }

    @SuppressLint("Range")
    fun getAlbumImageList(): MutableList<ImagePickerModel> {
        val imageItemList = ArrayList<ImagePickerModel>()

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            INDEX_MEDIA_ID,
            INDEX_MEDIA_URI,
            INDEX_ALBUM_NAME,
            INDEX_DATE_ADDED
        )
        val selection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Images.Media.SIZE + " > 0"
            else null
        val sortOrder = "$INDEX_DATE_ADDED DESC"
        val cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)

        if(cursor != null) {
            while(cursor.moveToNext()) {
                val mediaPath = cursor.getString(cursor.getColumnIndex(INDEX_MEDIA_URI))
                imageItemList.add(
                    ImagePickerModel(Uri.fromFile(File(mediaPath)), false)
                )
            }

            cursor.close()
            return imageItemList.toMutableList()
        } else {
            return imageItemList.toMutableList()
        }
    }
}