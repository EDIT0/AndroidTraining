package com.example.imagesenderdemo1.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesenderdemo1.data.util.NetworkManager
import com.example.imagesenderdemo1.domain.usecase.DeleteImageUseCase

class ImageZoomInOutViewModelFactory(
    private val app : Application,
    private val networkManager: NetworkManager,
    private val deleteImageUseCase: DeleteImageUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImageZoomInOutViewModel(
            app,
            networkManager,
            deleteImageUseCase
        ) as T
    }
}