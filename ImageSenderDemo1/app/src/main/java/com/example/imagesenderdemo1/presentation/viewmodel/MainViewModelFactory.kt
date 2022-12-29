package com.example.imagesenderdemo1.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesenderdemo1.data.util.NetworkManager
import com.example.imagesenderdemo1.domain.usecase.SaveImageToServerUseCase
import com.example.imagesenderdemo1.domain.usecase.SaveImagesToServerUseCase
import com.example.imagesenderdemo1.domain.usecase.SelectTotalSavedImageUseCase

class MainViewModelFactory(
    private val app : Application,
    private val networkManager: NetworkManager,
    private val saveImageToServerUseCase: SaveImageToServerUseCase,
    private val saveImagesToServerUseCase: SaveImagesToServerUseCase,
    private val selectTotalSavedImageUseCase: SelectTotalSavedImageUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            app,
            networkManager,
            saveImageToServerUseCase,
            saveImagesToServerUseCase,
            selectTotalSavedImageUseCase
        ) as T
    }
}