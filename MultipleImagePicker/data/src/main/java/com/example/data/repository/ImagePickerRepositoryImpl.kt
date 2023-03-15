package com.example.data.repository

import com.example.data.repository.localDataSource.ImagePickerLocalDataSource
import com.example.domain.model.ImagePickerModel
import com.example.domain.repository.ImagePickerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImagePickerRepositoryImpl @Inject constructor(
    private val imagePickerLocalDataSource: ImagePickerLocalDataSource
) : ImagePickerRepository{
    override suspend fun getAlbumImageList(): Flow<List<ImagePickerModel>> {
        return flow {
            emit(imagePickerLocalDataSource.getAlbumImageList())
        }
    }
}