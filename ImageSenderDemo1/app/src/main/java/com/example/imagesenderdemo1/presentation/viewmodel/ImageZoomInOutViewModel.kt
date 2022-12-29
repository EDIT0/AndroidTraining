package com.example.imagesenderdemo1.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.imagesenderdemo1.data.util.ERROR
import com.example.imagesenderdemo1.data.util.NetworkManager
import com.example.imagesenderdemo1.data.util.SingleLiveEvent
import com.example.imagesenderdemo1.domain.usecase.DeleteImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageZoomInOutViewModel(
    val app: Application,
    val networkManager: NetworkManager,
    val deleteImageUseCase: DeleteImageUseCase
) : AndroidViewModel(app){

    private val TAG = ImageZoomInOutViewModel::class.java.simpleName

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isSuccess = SingleLiveEvent<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun deleteImage(imageIdx: Int) {
        if(!networkManager.checkNetworkState()) {
            _isSuccess.value = false
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = deleteImageUseCase.execute(imageIdx)
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
                    Log.i(TAG, "이미지 삭제: ${it}")
                    withContext(Dispatchers.Main) {
                        _isSuccess.value = it.isSuccess
                    }
                }
        }
    }
}