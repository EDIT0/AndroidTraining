package com.example.databindingdemo2

import android.view.animation.Animation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(

) : ViewModel(){

    var etInput = MutableLiveData<String>("EDIT")
    val animType = 0
    val animDuration = 300

}