package com.example.data.repository.remoteDataSourceImpl

import com.example.data.api.ApiService
import com.example.data.repository.remoteDataSource.ImageRemoteDataSource
import com.example.data.util.Utility
import com.example.domain.model.IsSuccessSendImageModel
import retrofit2.Response
import java.io.File

class ImageRemoteDataSourceImpl(
    private val apiService: ApiService
) : ImageRemoteDataSource {
    override suspend fun saveImagesToServer(
        fileArray: ArrayList<File>,
        fileNameArray: ArrayList<String>
    ): Response<IsSuccessSendImageModel> {
        return apiService.sendImagesToServer(fileNameArray, Utility.multipartArrayBody(fileArray, fileNameArray))
    }
}