package com.example.mvvmarchitecturestudy.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mvvmarchitecturestudy.data.model.MovieModel
import com.example.mvvmarchitecturestudy.data.model.MovieModelResult
import com.example.mvvmarchitecturestudy.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val app : Application,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    private val deleteSavedMovieUseCase: DeleteSavedMovieUseCase,
    private val saveMovieUseCase: SaveMovieUseCase,
    private val getSearchSavedMoviesUseCase: GetSearchSavedMoviesUseCase
) : AndroidViewModel(app) {

    val popularMovies : MutableLiveData<Response<MovieModel>> = MutableLiveData()
    fun getPopularMovies(language : String, page : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = getPopularMoviesUseCase.execute(language, page)
            popularMovies.postValue(apiResult)
        }
    }

    val searchedMovies : MutableLiveData<Response<MovieModel>> = MutableLiveData()
    fun getSearchMovies(query : String, language: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = getSearchMoviesUseCase.execute(query, language, page)
            searchedMovies.postValue(apiResult)
        }
    }

//    fun getSavedMovies() = liveData {
//        getSavedMoviesUseCase.execute().collect {
//            emit(it)
//        }
//    }

    fun deleteSavedMovie(movieModelResult: MovieModelResult) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSavedMovieUseCase.execute(movieModelResult)
        }

    }

    fun saveMovie(movieModelResult: MovieModelResult) {
        viewModelScope.launch(Dispatchers.IO) {
            saveMovieUseCase.execute(movieModelResult)
        }
    }

    var keyword = MutableLiveData<String>("")
    val getSavedMovies: LiveData<List<MovieModelResult>> = Transformations.switchMap(keyword) { param->
        getSearchSavedMoviesUseCase.execute(param)
    }
//    var getSavedMovies : MutableLiveData<List<MovieModelResult>> = MutableLiveData()
//    fun getSearchSavedMovies(keyword : String) {
////        Log.i("MYTAG", "뷰모델 ${keyword} ${it.size}")

//        getSearchSavedMoviesUseCase.execute(keyword).collect {
//            Log.i("MYTAG", "뷰모델 ${keyword} ${it.size}")
//            getSavedMovies.postValue(it)
//        }
//    }

//    fun getSearchSavedMovies(keyword : String) = liveData {
//        getSearchSavedMoviesUseCase.execute(keyword).collect {
//            emit(it)
//        }
//    }
}