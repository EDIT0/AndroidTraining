package com.example.mvpexample1.presenter

import androidx.lifecycle.MutableLiveData
import com.example.mvpexample1.model.data.MovieModel
import com.example.mvpexample1.model.db.MovieDao
import com.example.mvpexample1.model.network.ApiService

class SavedMoviesPresenter(
//    private val savedContractView: SavedMoviesContract.View,
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : SavedMoviesContract.Presenter {

    private lateinit var savedContractView: SavedMoviesContract.View

    val savedMovieList = MutableLiveData<ArrayList<MovieModel.MovieModelResult>>()
    override suspend fun getSavedMovies() {
        try {
            val result = movieDao.getAllSavedMovies()

            result.collect {
                savedMovieList.postValue(it as ArrayList)
            }

        } catch (e : Exception) {

        }
    }

    override suspend fun saveMovie(data: MovieModel.MovieModelResult) {
        movieDao.insertMovie(data)
    }

    override suspend fun deleteMovie(data: MovieModel.MovieModelResult) {
        movieDao.deleteSavedMovies(data)
    }

    override fun setView(view: SavedMoviesContract.View) {
        savedContractView = view
        savedContractView.showToast("setView")
    }

    override fun releaseView() {
        savedContractView.showToast("releaseView")
    }
}