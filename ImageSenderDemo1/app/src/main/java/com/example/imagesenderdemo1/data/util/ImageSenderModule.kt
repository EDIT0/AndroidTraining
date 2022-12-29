package com.example.imagesenderdemo1.data.util

import com.example.imagesenderdemo1.BuildConfig
import com.example.imagesenderdemo1.data.api.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class ImageSenderModule {

    companion object mo {
        private var instance: ImageSenderModule? = null

        @JvmStatic
        fun getInstance(): ImageSenderModule = instance
            ?: synchronized(this){
                instance
                    ?: ImageSenderModule().also {
                        instance = it
                    }
            }
    }

    fun BaseModule(): ApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
        val client: ApiService = retrofit.create(ApiService::class.java)

        return client
    }

//    fun SendImageModule(file: File, fileName: String): Pair<ApiService, MultipartBody.Part>{
//        var requestBody : RequestBody = RequestBody.create(MediaType.parse("image/jpg"),file)
//        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)
//
//        var retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BuildConfig.BASE_URL)
//            .build()
//
//        var server = retrofit.create(ApiService::class.java)
//
//        return Pair(server,body)
//    }
}