package com.example.data.repository.localDataSource

import com.example.domain.model.ImagePickerModel
import kotlinx.coroutines.flow.Flow

interface ImagePickerLocalDataSource {
    suspend fun getAlbumImageList() : List<ImagePickerModel>
}