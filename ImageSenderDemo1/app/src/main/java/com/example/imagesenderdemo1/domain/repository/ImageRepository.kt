package com.example.imagesenderdemo1.domain.repository

import com.example.imagesenderdemo1.data.model.DeleteImageModel
import com.example.imagesenderdemo1.data.model.IsSuccessSendImageModel
import com.example.imagesenderdemo1.data.model.SelectTotalSavedImageModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

interface ImageRepository {
    suspend fun sendImageToServer(file: File, fileName: String) : Flow<IsSuccessSendImageModel>
    suspend fun sendImagesToServer(fileArray: ArrayList<File>, fileNameArray: ArrayList<String>) : Flow<IsSuccessSendImageModel>
    suspend fun selectTotalSavedImage(key: String, page: Int) : Flow<SelectTotalSavedImageModel>
    suspend fun deleteImage(imageIdx: Int) : Flow<DeleteImageModel>
}