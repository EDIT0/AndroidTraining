package com.example.imagesenderdemo1.data.repository

import android.util.Log
import com.example.imagesenderdemo1.data.model.DeleteImageModel
import com.example.imagesenderdemo1.data.model.IsSuccessSendImageModel
import com.example.imagesenderdemo1.data.model.SelectTotalSavedImageModel
import com.example.imagesenderdemo1.data.repository.remote.ImageRemoteDataSource
import com.example.imagesenderdemo1.data.util.ERROR
import com.example.imagesenderdemo1.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class ImageRepositoryImpl(
    private val imageRemoteDataSource: ImageRemoteDataSource
) : ImageRepository {
    override suspend fun sendImageToServer(
        file: File,
        fileName: String
    ): Flow<IsSuccessSendImageModel> {
        return flow {
            val response = imageRemoteDataSource.sendImageToServer(file, fileName)
            if(response.isSuccessful) {
                if(response.body()?.isSuccess == true) {
                    emit(response.body()!!)
                } else {
                    throw Exception(ERROR)
                }
            } else {
                throw Exception(ERROR)
            }
        }
    }

    override suspend fun sendImagesToServer(
        fileArray: ArrayList<File>,
        fileNameArray: ArrayList<String>
    ): Flow<IsSuccessSendImageModel> {
        return flow {
            val response = imageRemoteDataSource.sendImagesToServer(fileArray, fileNameArray)
            if(response.isSuccessful) {
                if(response.body()?.isSuccess == true) {
                    emit(response.body()!!)
                } else {
                    throw Exception(ERROR)
                }
            } else {
                throw Exception(ERROR)
            }
        }
    }

    override suspend fun selectTotalSavedImage(key: String, page: Int): Flow<SelectTotalSavedImageModel> {
        return flow {
            val response = imageRemoteDataSource.selectTotalSavedImage(key, page)
            if(response.isSuccessful) {
                if(response.body()?.selectTotalSavedImageModel?.isNotEmpty() == true) {
                    emit(response.body()!!)
                } else {
                    throw Exception(ERROR)
                }
            } else {
                throw Exception(ERROR)
            }
        }
    }

    override suspend fun deleteImage(imageIdx: Int): Flow<DeleteImageModel> {
        return flow {
            val response = imageRemoteDataSource.deleteImage(imageIdx)
            if(response.isSuccessful) {
                if(response.body()?.isSuccess == true) {
                    emit(response.body()!!)
                } else {
                    throw Exception(ERROR)
                }
            } else {
                throw Exception(ERROR)
            }
        }
    }
}