package com.example.databindingdemo2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.databindingdemo2.model.TextModel

class TextRecyclerViewModel(

) : ViewModel() {
    private var _textList: MutableLiveData<MutableList<TextModel>> =
        MutableLiveData<MutableList<TextModel>>(
//            mutableListOf(TextModel("a"), TextModel("b"), TextModel("c"))
        )
    val textList get() = _textList

    fun addItem(inputEditText: String) {
        if(_textList.value == null) {
            _textList.setValue(MutableLiveData<MutableList<TextModel>>(mutableListOf(TextModel(inputEditText))).value)
            return
        }
        Log.i("MYTAG", "1 ${_textList.value}")
        Log.i("MYTAG", "1_1 ${_textList.hashCode()}")
        val tempList = _textList.value
        tempList?.addAll(mutableListOf(TextModel(inputEditText)))
        _textList.value = tempList // postValue는 비동기
        Log.i("MYTAG", "2 ${_textList.value}")
    }
}