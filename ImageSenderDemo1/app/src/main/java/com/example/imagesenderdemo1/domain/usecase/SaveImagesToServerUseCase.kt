package com.example.imagesenderdemo1.domain.usecase

import com.example.imagesenderdemo1.data.model.IsSuccessSendImageModel
import com.example.imagesenderdemo1.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class SaveImagesToServerUseCase(
    private val imageRepository: ImageRepository
) {
    suspend fun execute(
        fileArray: ArrayList<File>,
        fileNameArray: ArrayList<String>
    ): Flow<IsSuccessSendImageModel> {
        return imageRepository.sendImagesToServer(fileArray, fileNameArray)
    }
}