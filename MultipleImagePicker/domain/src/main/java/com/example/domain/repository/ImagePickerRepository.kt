package com.example.domain.repository

import com.example.domain.model.ImagePickerModel
import kotlinx.coroutines.flow.Flow

interface ImagePickerRepository {
    suspend fun getAlbumImageList() : Flow<List<ImagePickerModel>>
}