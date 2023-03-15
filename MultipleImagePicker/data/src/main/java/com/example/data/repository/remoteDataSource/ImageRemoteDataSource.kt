package com.example.data.repository.remoteDataSource

import com.example.domain.model.IsSuccessSendImageModel
import retrofit2.Response
import java.io.File

interface ImageRemoteDataSource {
    suspend fun saveImagesToServer(fileArray: ArrayList<File>, fileNameArray: ArrayList<String>) : Response<IsSuccessSendImageModel>
}