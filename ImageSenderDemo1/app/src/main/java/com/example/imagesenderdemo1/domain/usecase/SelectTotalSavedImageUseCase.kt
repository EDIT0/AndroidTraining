package com.example.imagesenderdemo1.domain.usecase

import com.example.imagesenderdemo1.data.model.IsSuccessSendImageModel
import com.example.imagesenderdemo1.data.model.SelectTotalSavedImageModel
import com.example.imagesenderdemo1.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class SelectTotalSavedImageUseCase(
    private val imageRepository: ImageRepository
) {
    suspend fun execute(
        key: String,
        page: Int
    ): Flow<SelectTotalSavedImageModel> {
        return imageRepository.selectTotalSavedImage(key, page)
    }
}