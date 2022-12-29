package com.example.imagesenderdemo1.data.repository.remote

import android.util.Log
import com.example.imagesenderdemo1.data.api.ApiService
import com.example.imagesenderdemo1.data.model.DeleteImageModel
import com.example.imagesenderdemo1.data.model.IsSuccessSendImageModel
import com.example.imagesenderdemo1.data.model.SelectTotalSavedImageModel
import com.example.imagesenderdemo1.data.util.Utility
import retrofit2.Response
import java.io.File

class ImageRemoteDataSourceImpl(
    private val apiService: ApiService
) : ImageRemoteDataSource {
    override suspend fun sendImageToServer(
        file: File,
        fileName: String
    ): Response<IsSuccessSendImageModel> {
        return apiService.sendImageToServer(fileName, Utility.multipartBody(file, fileName))
    }

    override suspend fun sendImagesToServer(
        fileArray: ArrayList<File>,
        fileNameArray: ArrayList<String>
    ): Response<IsSuccessSendImageModel> {
        return apiService.sendImagesToServer(fileNameArray, Utility.multipartArrayBody(fileArray, fileNameArray))
    }

    override suspend fun selectTotalSavedImage(key: String, page: Int): Response<SelectTotalSavedImageModel> {
        return apiService.selectTotalSavedImage(key, page)
    }

    override suspend fun deleteImage(imageIdx: Int): Response<DeleteImageModel> {
        return apiService.deleteImage(imageIdx)
    }


}