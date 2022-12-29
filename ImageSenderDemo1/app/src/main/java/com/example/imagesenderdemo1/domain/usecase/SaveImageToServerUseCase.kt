package com.example.imagesenderdemo1.domain.usecase

import com.example.imagesenderdemo1.data.model.IsSuccessSendImageModel
import com.example.imagesenderdemo1.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class SaveImageToServerUseCase(
    private val imageRepository: ImageRepository
) {
    suspend fun execute(
        file: File,
        fileName: String
    ): Flow<IsSuccessSendImageModel> {
        return imageRepository.sendImageToServer(file, fileName)
    }
}