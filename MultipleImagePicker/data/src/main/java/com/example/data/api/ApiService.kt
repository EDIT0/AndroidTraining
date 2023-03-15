package com.example.data.api

import com.example.domain.model.IsSuccessSendImageModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("SendImagesToServer.php")
    suspend fun sendImagesToServer(
        @Part("fileNameArray") fileNameArray: ArrayList<String>,
        @Part imageFileArray : ArrayList<MultipartBody.Part>
    ): Response<IsSuccessSendImageModel>
}