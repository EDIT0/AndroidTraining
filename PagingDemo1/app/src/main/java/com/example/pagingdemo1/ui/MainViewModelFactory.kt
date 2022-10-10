package com.example.pagingdemo1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pagingdemo1.usecase.GetPopularMovieUseCase

class MainViewModelFactory(
    private val getPopularMovieUseCase: GetPopularMovieUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getPopularMovieUseCase
        ) as T
    }
}