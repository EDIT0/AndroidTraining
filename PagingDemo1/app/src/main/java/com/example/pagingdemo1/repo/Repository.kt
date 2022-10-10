package com.example.pagingdemo1.repo

import androidx.paging.PagingData
import com.example.pagingdemo1.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPopularMovies(language : String, page : Int) : Flow<PagingData<MovieModel.MovieModelResult>>
}