package com.example.pagingdemo1.repo

import androidx.paging.PagingData
import com.example.pagingdemo1.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface Repository {
    fun getPopularMovies(movieModel: MutableSharedFlow<MovieModel>, query: String, language : String, page : Int) : Flow<PagingData<MovieModel.MovieModelResult>>
}