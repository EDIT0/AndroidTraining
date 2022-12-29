package com.example.imagesenderdemo1.data.repository.remote

import com.example.imagesenderdemo1.data.model.DeleteImageModel
import com.example.imagesenderdemo1.data.model.IsSuccessSendImageModel
import com.example.imagesenderdemo1.data.model.SelectTotalSavedImageModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

interface ImageRemoteDataSource {
    suspend fun sendImageToServer(file: File, fileName: String) : Response<IsSuccessSendImageModel>
    suspend fun sendImagesToServer(fileArray: ArrayList<File>, fileNameArray: ArrayList<String>) : Response<IsSuccessSendImageModel>
    suspend fun selectTotalSavedImage(key: String, page: Int) : Response<SelectTotalSavedImageModel>
    suspend fun deleteImage(imageIdx: Int) : Response<DeleteImageModel>
}