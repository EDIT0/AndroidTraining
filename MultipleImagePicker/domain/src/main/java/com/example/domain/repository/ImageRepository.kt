package com.example.domain.repository

import com.example.domain.model.IsSuccessSendImageModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ImageRepository {
    suspend fun saveImagesToServer(fileArray: ArrayList<File>, fileNameArray: ArrayList<String>) : Flow<IsSuccessSendImageModel>
}