package com.example.imagesenderdemo1.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.imagesenderdemo1.data.model.SavedImageModel
import com.example.imagesenderdemo1.data.model.SelectTotalSavedImageModel
import com.example.imagesenderdemo1.data.util.ERROR
import com.example.imagesenderdemo1.data.util.NetworkManager
import com.example.imagesenderdemo1.data.util.SingleLiveEvent
import com.example.imagesenderdemo1.domain.usecase.SaveImageToServerUseCase
import com.example.imagesenderdemo1.domain.usecase.SaveImagesToServerUseCase
import com.example.imagesenderdemo1.domain.usecase.SelectTotalSavedImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainViewModel(
    val app: Application,
    val networkManager: NetworkManager,
    val saveImageToServerUseCase: SaveImageToServerUseCase,
    val saveImagesToServerUseCase: SaveImagesToServerUseCase,
    val selectTotalSavedImageUseCase: SelectTotalSavedImageUseCase
) : AndroidViewModel(app){

    private val TAG = MainViewModel::class.java.simpleName

    private val _isSuccess = SingleLiveEvent<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun saveImage(file: File, fileName: String) {
        if(!networkManager.checkNetworkState()) {
            _isSuccess.value = false
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = saveImageToServerUseCase.execute(file, fileName)
            apiResult
                .onStart {
                    Log.i(TAG, "saveImage onStart")
                    withContext(Dispatchers.Main) {
                        _isLoading.value = true
                    }
                }
                .onCompletion {
                    Log.i(TAG, "saveImage onCompletion")
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                }
                .catch { e ->
                    when (e.message) {
                        ERROR -> {
                            Log.i(TAG, "Error ${e.message}")
                        }
                        else -> {
                            Log.i(TAG, "??? ${e.message}")
                        }
                    }
                    withContext(Dispatchers.Main) {
                        _isSuccess.value = false
                    }
                }
                .collect {
                    Log.i(TAG, "이미지 저장: ${it}")
                    withContext(Dispatchers.Main) {
                        _isSuccess.value = it.isSuccess
                    }
                }
        }
    }

    fun saveImages(fileArray: ArrayList<File>, fileNameArray: ArrayList<String>) {
        if(!networkManager.checkNetworkState()) {
            _isSuccess.value = false
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = saveImagesToServerUseCase.execute(fileArray, fileNameArray)
            apiResult
                .onStart {
                    Log.i(TAG, "saveImage onStart")
                    withContext(Dispatchers.Main) {
                        _isLoading.value = true
                    }
                }
                .onCompletion {
                    Log.i(TAG, "saveImage onCompletion")
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                }
                .catch { e ->
                    when (e.message) {
                        ERROR -> {
                            Log.i(TAG, "Error ${e.message}")
                        }
                        else -> {
                            Log.i(TAG, "??? ${e.message}")
                        }
                    }
                    withContext(Dispatchers.Main) {
                        _isSuccess.value = false
                    }
                }
                .collect {
                    Log.i(TAG, "이미지 저장: ${it}")
                    withContext(Dispatchers.Main) {
                        _isSuccess.value = it.isSuccess
                    }
                }
        }
    }


//    private val _selectTotalSavedImage = SingleLiveEvent<SelectTotalSavedImageModel>()
//    val selectTotalSavedImage: LiveData<SelectTotalSavedImageModel> get() = _selectTotalSavedImage

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _selectTotalSavedImage = MutableLiveData<SelectTotalSavedImageModel>()
    val selectTotalSavedImage: LiveData<SelectTotalSavedImageModel> get() = _selectTotalSavedImage

    var page = 1

    fun init() {
        page = 1
        var currentList = _selectTotalSavedImage.value
        currentList = SelectTotalSavedImageModel(ArrayList())
        _selectTotalSavedImage.value = currentList as SelectTotalSavedImageModel
    }

    fun selectTotalSavedImage(key: String) {
        if(!networkManager.checkNetworkState()) {
            _isSuccess.value = false
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = selectTotalSavedImageUseCase.execute(key, page)
            apiResult
                .onStart {
                    Log.i(TAG, "selectTotalSavedImage onStart")
                    withContext(Dispatchers.Main) {
                        _isLoading.value = true
                    }
                }
                .onCompletion {
                    Log.i(TAG, "selectTotalSavedImage onCompletion")
                    withContext(Dispatchers.Main) {
                        _isLoading.value = false
                    }
                }
                .catch { e ->
                    when (e.message) {
                        ERROR -> {
                            Log.i(TAG, "Error ${e.message}")
                        }
                        else -> {
                            Log.i(TAG, "??? ${e.message}")
                        }
                    }
                }
                .collect {
                    Log.i(TAG, "가져온 이미지: ${it}")
                    withContext(Dispatchers.Main) {
                        var currentList : SelectTotalSavedImageModel
                        if(_selectTotalSavedImage.value != null) {
                            currentList = _selectTotalSavedImage.value!!
                            currentList.selectTotalSavedImageModel.addAll(it.selectTotalSavedImageModel)
                        } else {
                            currentList = it
                        }

                        _selectTotalSavedImage.value = currentList

                        page++
                    }
                }
        }
    }
}