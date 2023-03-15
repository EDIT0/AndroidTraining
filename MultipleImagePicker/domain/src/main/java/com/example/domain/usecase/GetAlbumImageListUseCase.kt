package com.example.domain.usecase

import com.example.domain.model.ImagePickerModel
import com.example.domain.repository.ImagePickerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumImageListUseCase @Inject constructor (
    private val imagePickerRepository: ImagePickerRepository
) {
    suspend fun execute() : Flow<List<ImagePickerModel>> {
        return imagePickerRepository.getAlbumImageList()
    }
}