package com.example.flowdemo1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val mLiveData1 = MutableLiveData<String>()
    val mStateFlow1 = MutableStateFlow<String>("init")
    val mStateFlow2 = MutableStateFlow<String>("init")
    val mSharedFlow1 = MutableSharedFlow<String>()
    val mSharedFlow2 = MutableSharedFlow<String>()

    private var number = 0
    var numberStateFlow = MutableStateFlow<Int>(0)

    fun plusNumber() {
        viewModelScope.launch {
            number++
            numberStateFlow.emit(number)
        }
    }

}