package com.example.eventbus.otto

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel(

) : ViewModel() {
    var str = MutableLiveData<String>()

    fun setData(data: String) {
        str.value = data
    }
}