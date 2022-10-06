package com.example.databindingdemo2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.databindingdemo2.R

class InputEditTextViewModel(

) : ViewModel(){
    var etInput = MutableLiveData<String>("EDIT")
    val animTypeIn = R.anim.fade_in
    val animTypeOut = R.anim.fade_out
    val animDuration = 300
}