package com.example.imagesenderdemo1.presentation.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagesenderdemo1.data.model.SelectTotalSavedImageModel
import com.example.imagesenderdemo1.data.util.NetworkManager

class ImageDecoViewPagerViewModel(
    private val app: Application,
    val networkManager: NetworkManager
) : AndroidViewModel(app){

    private val _decoImageList = MutableLiveData<MutableList<Uri>>()
    val decoImageList: LiveData<MutableList<Uri>> get() = _decoImageList

    fun setDecoImageList(decoImageList: ArrayList<Uri>) {
        _decoImageList.value = decoImageList
    }

    fun changeImage(position: Int, newUri: Uri) {
        var currentList = _decoImageList.value
//        var item: Uri? = currentList?.get(position)
//        if (item != null) {
            currentList?.set(position, newUri)
//        }
        _decoImageList.value = currentList as ArrayList
        addCropImage(newUri)
    }

    var decoImageCount = MutableLiveData<Int>(0)
    var decoImageCurrentCount = MutableLiveData<Int>(0)

    private var _tempCropImageList = ArrayList<Uri>()
    private val _cropImageList = MutableLiveData<MutableList<Uri>>()
    val cropImageList: LiveData<MutableList<Uri>> get() = _cropImageList

    fun setCropImageList(cropImageList: ArrayList<Uri>) {
        _cropImageList.value = cropImageList
    }

    fun addCropImage(uri: Uri) {
        _tempCropImageList.add(uri)
        _cropImageList.value = _tempCropImageList.toMutableList()
    }

    fun deleteCropImage(uri: Uri) {
        _tempCropImageList.remove(uri)
        _cropImageList.value = _tempCropImageList.toMutableList()
    }
}