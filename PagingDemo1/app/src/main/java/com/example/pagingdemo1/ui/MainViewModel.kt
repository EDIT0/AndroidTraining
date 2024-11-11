package com.example.pagingdemo1.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.usecase.GetPopularMovieUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMovieUseCase: GetPopularMovieUseCase
) : ViewModel(){

    var isStart = true
//    var etInput : MutableLiveData<String> = MutableLiveData("good")

    // LiveData
//    var result : LiveData<PagingData<MovieModel.MovieModelResult>> = etInput.switchMap {
//        getPopularMovieUseCase.execute(it, "", 0).asLiveData().cachedIn(viewModelScope)
//    }
    val movieAdapter = MovieAdapter()

    private var _etInputStateFlow = MutableStateFlow<String>("Start")
    val etInputStateFlow = _etInputStateFlow.asStateFlow()
//    var resultStateFlow: StateFlow<PagingData<MovieModel.MovieModelResult>?> = _etInputStateFlow.flatMapLatest {
//        getPopularMovieUseCase.execute(it, "", 0)
//    }
//        .cachedIn(viewModelScope)
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Lazily,
//            initialValue = null
//        )

    fun emitEtInput(input: String) {
        viewModelScope.launch {
            _etInputStateFlow.emit(input)
        }
    }

    /**
     * 전체 데이터 처리 변수
     */
    private val _movieModel = MutableSharedFlow<MovieModel>()
    val movieModel = _movieModel.asSharedFlow()

    /**
     * 페이징 데이터 처리 변수
     */
    private val _pagingDataFlow = MutableStateFlow<PagingData<MovieModel.MovieModelResult>>(PagingData.empty())
    val pagingDataFlow: StateFlow<PagingData<MovieModel.MovieModelResult>> = _pagingDataFlow.asStateFlow()

    fun submitPagingData(pagingData: PagingData<MovieModel.MovieModelResult>) {
        _pagingDataFlow.value = pagingData
    }

    // etInputStateFlow에 따라 데이터가 변경될 때마다 새로운 PagingData 로드
    fun fetchPagingData() {
        viewModelScope.launch {
            etInputStateFlow.collectLatest { input ->
                getPopularMovieUseCase.execute(_movieModel, input, "", 0)
                    .cachedIn(viewModelScope)
                    .map { a ->
                        a.map { model ->
                            Log.d("MYTAG", "페이징 데이터 : ${model}")
                            model
                        }
                    }
                    .collect {
//                        submitPagingData(it)
                        _pagingDataFlow.value = it
                    }
            }
        }
    }

    // Flow
//    fun getPopularMovies() = getPopularMovieUseCase.execute(etInput.value.toString(), "", 0)
}