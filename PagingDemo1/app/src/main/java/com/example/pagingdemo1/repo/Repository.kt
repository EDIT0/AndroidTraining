package com.example.pagingdemo1.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pagingdemo1.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getPopularMovies(query: String, language : String, page : Int) : Flow<PagingData<MovieModel.MovieModelResult>>
}