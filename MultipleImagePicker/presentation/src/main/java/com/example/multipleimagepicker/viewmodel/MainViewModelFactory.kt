package com.example.multipleimagepicker.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.util.NetworkManager
import com.example.domain.usecase.GetAlbumImageListUseCase
import com.example.domain.usecase.SaveSelectedImagesToServerUseCase

class MainViewModelFactory(
    private val application: Application,
    private val networkManager: NetworkManager,
    private val saveSelectedImagesToServerUseCase: SaveSelectedImagesToServerUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            application,
            networkManager,
            saveSelectedImagesToServerUseCase
        ) as T
    }
}