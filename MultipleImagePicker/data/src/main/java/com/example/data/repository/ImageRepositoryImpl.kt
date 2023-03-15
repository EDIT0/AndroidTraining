package com.example.data.repository

import com.example.data.repository.remoteDataSource.ImageRemoteDataSource
import com.example.data.util.MessageSet
import com.example.domain.model.IsSuccessSendImageModel
import com.example.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class ImageRepositoryImpl(
    private val imageRemoteDataSource: ImageRemoteDataSource
) : ImageRepository{
    override suspend fun saveImagesToServer(
        fileArray: ArrayList<File>,
        fileNameArray: ArrayList<String>
    ): Flow<IsSuccessSendImageModel> {
        return flow {
            val response = imageRemoteDataSource.saveImagesToServer(fileArray, fileNameArray)
            if(response.isSuccessful) {
                if(response.body()?.isSuccess == true) {
                    emit(response.body()!!)
                } else {
                    throw Exception(MessageSet.ERROR)
                }
            } else {
                throw Exception(MessageSet.ERROR)
            }
        }
    }
}