package com.example.pagingdemo1.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.usecase.GetPopularMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMovieUseCase: GetPopularMovieUseCase
) : ViewModel(){

//    var etInput : MutableLiveData<String> = MutableLiveData("good")

    // LiveData
//    var result : LiveData<PagingData<MovieModel.MovieModelResult>> = etInput.switchMap {
//        getPopularMovieUseCase.execute(it, "", 0).asLiveData().cachedIn(viewModelScope)
//    }

    private var _etInputStateFlow = MutableStateFlow<String>("Start")
    val etInputStateFlow = _etInputStateFlow.asStateFlow()
    var resultStateFlow: StateFlow<PagingData<MovieModel.MovieModelResult>?> = _etInputStateFlow.flatMapLatest {
        getPopularMovieUseCase.execute(it, "", 0)
    }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

    fun emitEtInput(input: String) {
        viewModelScope.launch {
            _etInputStateFlow.emit(input)
        }
    }

    // Flow
//    fun getPopularMovies() = getPopularMovieUseCase.execute(etInput.value.toString(), "", 0)
}