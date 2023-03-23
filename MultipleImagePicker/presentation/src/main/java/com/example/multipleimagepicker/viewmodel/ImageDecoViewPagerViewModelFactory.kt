package com.example.multipleimagepicker.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.util.NetworkManager

class ImageDecoViewPagerViewModelFactory(
    private val app : Application,
    private val networkManager: NetworkManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImageDecoViewPagerViewModel(
            app,
            networkManager
        ) as T
    }
}