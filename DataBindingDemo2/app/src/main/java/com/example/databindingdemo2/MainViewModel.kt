package com.example.databindingdemo2

import android.annotation.SuppressLint
import android.app.Application
import android.view.animation.Animation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager

class MainViewModel(

) : ViewModel(){

//    @SuppressLint("StaticFieldLeak")
//    private val context = getApplication<Application>().applicationContext

    var etInput = MutableLiveData<String>("EDIT")
    val animTypeIn = R.anim.fade_in
    val animTypeOut = R.anim.fade_out
    val animDuration = 300


//    private var _textList: MutableLiveData<MutableList<TextModel>> = MutableLiveData<MutableList<TextModel>>(
//        mutableListOf(TextModel("a"), TextModel("b"), TextModel("c"))
//    )
//    val textList get() = _textList
//
//    val textAdapter = TextAdapter()
//    val linearLayoutManager = LinearLayoutManager(application.applicationContext)
//
//    fun addItem() {
//        val tempList = _textList.value
//        tempList?.addAll(mutableListOf(TextModel("d"), TextModel("e"), TextModel("f")))
//        _textList.postValue(tempList)
//    }
}