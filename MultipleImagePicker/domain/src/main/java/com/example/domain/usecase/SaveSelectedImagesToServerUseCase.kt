package com.example.domain.usecase

import com.example.domain.model.IsSuccessSendImageModel
import com.example.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class SaveSelectedImagesToServerUseCase(
    private val imageRepository: ImageRepository
) {
    suspend fun execute(
        fileArray: ArrayList<File>,
        fileNameArray: ArrayList<String>
    ): Flow<IsSuccessSendImageModel> {
        return imageRepository.saveImagesToServer(fileArray, fileNameArray)
    }
}