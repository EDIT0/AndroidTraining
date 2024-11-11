package com.example.pagingdemo1.usecase

import androidx.paging.PagingData
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.repo.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GetPopularMovieUseCase(
    private val repository: Repository
) {
    fun execute(movieModel: MutableSharedFlow<MovieModel>, query: String, language: String, page: Int): Flow<PagingData<MovieModel.MovieModelResult>> {
        return repository.getPopularMovies(movieModel, query, language, page)
    }
}