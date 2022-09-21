package com.example.databindingdemo1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {

    val repo = Repository()

    var picturePath: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var number: Int = 0
    var info: MutableLiveData<String> = MutableLiveData("아무말")
    var isInfoVisible: MutableLiveData<Boolean> = MutableLiveData()

    fun getData() {
        repo.getData().also {
            picturePath.value = it.picturePath
            name.value = it.name
            number = it.number
            isInfoVisible.value = it.isInfoVisible
        }
    }

    fun getClickData() {
        repo.getClickData().also {
            picturePath.value = it.picturePath
            name.value = it.name
            number = it.number
            isInfoVisible.value = it.isInfoVisible
        }
    }
}