package com.example.databindingdemo2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager

class TextRecyclerViewModel(
    application: Application
) : AndroidViewModel(application) {
    private var _textList: MutableLiveData<MutableList<TextModel>> =
        MutableLiveData<MutableList<TextModel>>(
            mutableListOf(TextModel("a"), TextModel("b"), TextModel("c"))
        )
    val textList get() = _textList

    val textAdapter = TextAdapter()
    val linearLayoutManager = LinearLayoutManager(application.applicationContext)

    fun addItem() {
        val tempList = _textList.value
        tempList?.addAll(mutableListOf(TextModel("d"), TextModel("e"), TextModel("f")))
        _textList.postValue(tempList)
    }
}