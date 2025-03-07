package com.example.pagingdemo1.repo

import androidx.paging.PagingData
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.repo.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override fun getPopularMovies(movieModel: MutableSharedFlow<MovieModel>, query: String, language: String, page: Int): Flow<PagingData<MovieModel.MovieModelResult>> {
        return remoteDataSource.getPopularMovies(movieModel, query, language, page)
//        return flow {
//            val response = remoteDataSource.getPopularMovies(language, page)
//            response.collect {
//                emit(it)
//            }
//            emit(response)
//
//            if(response.isSuccessful) {
//                if(response.body()?.movieModelResults?.isEmpty() == true) {
//                    throw Exception(NO_DATA)
//                } else {
//                    response.body()?.let {
//                        emit(it)
//                    }
//                }
//            } else {
//                throw Exception(ERROR)
//            }
//        }
    }
}