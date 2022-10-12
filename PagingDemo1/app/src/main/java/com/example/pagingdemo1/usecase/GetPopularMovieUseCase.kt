package com.example.pagingdemo1.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.repo.Repository
import kotlinx.coroutines.flow.Flow

class GetPopularMovieUseCase(
    private val repository: Repository
) {
    fun execute(query: String, language: String, page: Int): Flow<PagingData<MovieModel.MovieModelResult>> {
        return repository.getPopularMovies(query, language, page)
    }
}