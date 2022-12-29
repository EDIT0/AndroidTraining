package com.example.imagesenderdemo1.data.api

import com.example.imagesenderdemo1.data.model.DeleteImageModel
import com.example.imagesenderdemo1.data.model.IsSuccessSendImageModel
import com.example.imagesenderdemo1.data.model.SelectTotalSavedImageModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("SendImageToServer.php")
    suspend fun sendImageToServer(
        @Part("fileName") fileName: String,
        @Part imageFile : MultipartBody.Part
    ): Response<IsSuccessSendImageModel>

    @Multipart
    @POST("SendImagesToServer.php")
    suspend fun sendImagesToServer(
        @Part("fileNameArray") fileNameArray: ArrayList<String>,
        @Part imageFileArray : ArrayList<MultipartBody.Part>
    ): Response<IsSuccessSendImageModel>

    @FormUrlEncoded
    @POST("SelectTotalSavedImage.php")
    suspend fun selectTotalSavedImage(
        @Field("key") key: String,
        @Field("page") page: Int
    ): Response<SelectTotalSavedImageModel>

    @FormUrlEncoded
    @POST("DeleteImage.php")
    suspend fun deleteImage(
        @Field("imageIdx") imageIdx: Int
    ): Response<DeleteImageModel>

//    @FormUrlEncoded
//    @POST("SendImagesToServer.php")
//    suspend fun sendImagesToServer(
//        @Field("fileNameArray") fileNameArray: ArrayList<String>,
//        @Field("imageFileArray") imageFileArray : ArrayList<MultipartBody.Part>
//    ): Response<IsSuccessSendImageModel>
}