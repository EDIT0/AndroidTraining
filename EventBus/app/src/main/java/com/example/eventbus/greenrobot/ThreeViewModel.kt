package com.example.eventbus.greenrobot

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThreeViewModel(

) : ViewModel() {

    var str = MutableLiveData<String>()

    fun setData(data: String) {
        str.value = data
    }
}