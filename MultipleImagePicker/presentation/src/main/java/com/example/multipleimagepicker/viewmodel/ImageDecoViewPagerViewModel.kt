package com.example.multipleimagepicker.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.util.NetworkManager
import com.example.data.util.Utility
import com.example.domain.model.ImagePickerModel

class ImageDecoViewPagerViewModel(
    private val app: Application,
    private val networkManager: NetworkManager
) : AndroidViewModel(app){

    /**
     * 데코 액티비티 들어올 때 배열
     * 이미지 수정 후 이 배열에 수정된 이미지 uri를 담아서 보낸다.
     * */
    private val _originalImageList = MutableLiveData<MutableList<ImagePickerModel>>()
    val originalImageList: LiveData<MutableList<ImagePickerModel>> get() = _originalImageList

    fun setOriginalImageList(originalList: ArrayList<ImagePickerModel>) {
        _originalImageList.value = originalList
    }

    /**
     * 수정 중인 이미지들을 가지고 있는 배열
     * */
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
    }

    var decoImageCount = MutableLiveData<Int>(0)
    var decoImageCurrentCount = MutableLiveData<Int>(0)

    /**
     * 오리지널 배열에 uri 옮겨담기
     * */
    fun saveDecoImageListUriToOriginalImageList() {
        for(i in 0 until originalImageList.value?.size!!) {
            val changedUri = decoImageList.value?.get(i)
//            val fileUri = Utility.contentUriToFileUri(app, changedUri)
            val model = ImagePickerModel(changedUri!!, originalImageList.value?.get(i)?.isChecked!!)
            originalImageList.value?.set(i, model)
        }
    }
}