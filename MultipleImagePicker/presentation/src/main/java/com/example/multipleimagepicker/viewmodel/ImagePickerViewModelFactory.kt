package com.example.multipleimagepicker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.GetAlbumImageListUseCase

class ImagePickerViewModelFactory(
    private val getAlbumImageListUseCase: GetAlbumImageListUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImagePickerViewModel(
            getAlbumImageListUseCase
        ) as T
    }
}