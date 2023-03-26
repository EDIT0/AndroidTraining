package com.example.multipleimagepicker.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.util.MessageSet
import com.example.data.util.SingleLiveEvent
import com.example.domain.model.ImagePickerModel
import com.example.domain.model.ViewType
import com.example.domain.usecase.GetAlbumImageListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImagePickerViewModel (
    private val getAlbumImageListUseCase: GetAlbumImageListUseCase
): ViewModel() {

    private val _eventObserver = SingleLiveEvent<String>()
    val eventObserver: LiveData<String> get() = _eventObserver

    private val _selectedImageItemList = MutableLiveData<MutableList<ImagePickerModel>>(mutableListOf())
    val selectedImageItemList: LiveData<MutableList<ImagePickerModel>> = _selectedImageItemList

    private val _imageItemList = MutableLiveData<MutableList<ImagePickerModel>>(mutableListOf())
    val imageItemList: LiveData<MutableList<ImagePickerModel>> = _imageItemList

    fun addCameraItem(list: ArrayList<ImagePickerModel>) {
        list.add(0, ImagePickerModel(Uri.parse("") ,false, ViewType.CAMERA))
        _imageItemList.value = list as ArrayList
    }

    private var imageLimitationCount = 10

    fun setImageLimitationCount(count: Int) {
        imageLimitationCount = count
    }

    fun getImageLimitationCount() = imageLimitationCount

    fun getAlbumImageList() {
        viewModelScope.launch(Dispatchers.Default) {
            getAlbumImageListUseCase.execute().collect {
                withContext(Dispatchers.Main) {
                    _imageItemList.value = it as ArrayList
                }
            }
        }
    }

    fun getCheckedImageUriList(): MutableList<String> {
        val checkedImageUriList = mutableListOf<String>()
        _imageItemList.value?.let {
            for(imageItem in _imageItemList.value!!) {
                if(imageItem.isChecked) checkedImageUriList.add(imageItem.uri.toString())
            }
        }
        return checkedImageUriList
    }

    /**
     * 이미지 선택을 선택 또는 취소 가능
     * */
    fun setCheckedForImagePicker(position: Int, value: Boolean) {
        val selectedImageSize = _selectedImageItemList.value?.size?:0
        Log.i("MYTAG", "${selectedImageSize}")
        if(selectedImageSize >= imageLimitationCount) {
            if(!value) {
                checkReverse(position, value)
            } else {
                _eventObserver.value = MessageSet.IMAGE_COUNT_LIMITATION
            }
            return
        }

        checkReverse(position, value)
    }

    private fun checkReverse(position: Int, value: Boolean) {
        var currentList = _imageItemList.value
        var item: ImagePickerModel? = currentList?.get(position)
        item?.isChecked = value
        if (item != null) {
            currentList?.set(position, item)
        }
        _imageItemList.value = currentList as ArrayList

        if(item?.isChecked == true) {
            var currentSelectedImageList = _selectedImageItemList.value
            if (item != null) {
                currentSelectedImageList?.add(item)
            }
            _selectedImageItemList.value = currentSelectedImageList as ArrayList
        } else {
            var currentSelectedImageList = _selectedImageItemList.value
            if (item != null) {
                currentSelectedImageList?.remove(item)
            }
            _selectedImageItemList.value = currentSelectedImageList as ArrayList
        }
    }

    /**
     * 선택된 이미지 취소
     * */
    fun setCheckedForSelectedImage(position: Int) {
        var currentSelectedImageList = _selectedImageItemList.value
        var item: ImagePickerModel? = currentSelectedImageList?.get(position)
        if (item != null) {
            currentSelectedImageList?.remove(item)
        }

        _selectedImageItemList.value = currentSelectedImageList as ArrayList

        item?.isChecked = false
        var currentList = _imageItemList.value
        var currentListPosition = currentList?.indexOf(item)
        if (currentListPosition != null && item != null) {
            currentList?.set(currentListPosition, item)
        }
        _imageItemList.value = currentList as ArrayList

    }
}