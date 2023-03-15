package com.example.data.repository.localDataSourceImpl

import android.util.Log
import com.example.data.repository.localDataSource.ImagePickerLocalDataSource
import com.example.data.util.ImagePicker
import com.example.domain.model.ImagePickerModel
import javax.inject.Inject

class ImagePickerLocalDataSourceImpl @Inject constructor(
    private val imagePicker: ImagePicker
) : ImagePickerLocalDataSource {
    override suspend fun getAlbumImageList(): List<ImagePickerModel> {
        val a = imagePicker.getAlbumImageList()
        Log.i("MYTAG", "정답?. ${a.size}")
        return a
    }
}