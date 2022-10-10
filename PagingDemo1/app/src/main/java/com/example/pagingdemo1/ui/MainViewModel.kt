package com.example.pagingdemo1.ui

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.usecase.GetPopularMovieUseCase
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val getPopularMovieUseCase: GetPopularMovieUseCase
) : ViewModel(){

    suspend fun getPopularMovies() : Flow<PagingData<MovieModel.MovieModelResult>> {
        return getPopularMovieUseCase.execute("", 0)
    }




//    Log.i("MYTAG", "현재 스레드 collect: ${Thread.currentThread().name}\n${it}")
}