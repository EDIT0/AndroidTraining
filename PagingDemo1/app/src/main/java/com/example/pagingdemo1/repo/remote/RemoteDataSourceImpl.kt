package com.example.pagingdemo1.repo.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.MoviePagingSource
import com.example.pagingdemo1.network.Service
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val service: Service
) : RemoteDataSource {
    //    override suspend fun getPopularMovies(language: String, page: Int): Response<MovieModel> {
//        return service.getPopularMovies(BuildConfig.API_KEY, language, page)
//    }
    override suspend fun getPopularMovies(language: String, page: Int): Flow<PagingData<MovieModel.MovieModelResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 1000,
                enablePlaceholders = false
            ),
            // 사용할 메소드 선언
            pagingSourceFactory = { MoviePagingSource(service)}
        ).flow

    }
}