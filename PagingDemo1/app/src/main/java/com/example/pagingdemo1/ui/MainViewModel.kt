package com.example.pagingdemo1.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.usecase.GetPopularMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMovieUseCase: GetPopularMovieUseCase
) : ViewModel(){

    var etInput : MutableLiveData<String> = MutableLiveData("good")
    var alert = false

    var result : LiveData<PagingData<MovieModel.MovieModelResult>> = etInput.switchMap {
        getPopularMovieUseCase.execute(it, "", 0).asLiveData()
    }



//    Flow<PagingData<MovieModel.MovieModelResult>>
    fun getPopularMovies() = getPopularMovieUseCase.execute(etInput.value.toString(), "", 0)

//    private val myCustomPosts2 : MutableLiveData<String> = MutableLiveData()
//
//    // 라이브 데이터 변경 시 다른 라이브 데이터 발행
//    val result = myCustomPosts2.switchMap { queryString ->
//        getPopularMovieUseCase.execute("", 0).cachedIn(viewModelScope)
//    }


//    Log.i("MYTAG", "현재 스레드 collect: ${Thread.currentThread().name}\n${it}")
}