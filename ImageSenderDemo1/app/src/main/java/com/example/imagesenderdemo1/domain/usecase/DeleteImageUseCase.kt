package com.example.imagesenderdemo1.domain.usecase

import com.example.imagesenderdemo1.data.model.DeleteImageModel
import com.example.imagesenderdemo1.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

class DeleteImageUseCase(
    private val imageRepository: ImageRepository
) {
    suspend fun execute(
        imageIdx: Int
    ): Flow<DeleteImageModel> {
        return imageRepository.deleteImage(imageIdx)
    }
}