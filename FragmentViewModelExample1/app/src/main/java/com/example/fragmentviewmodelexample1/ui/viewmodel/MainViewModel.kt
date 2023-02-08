package com.example.fragmentviewmodelexample1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fragmentviewmodelexample1.model.ItemModel

class MainViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    private val _itemList = MutableLiveData<MutableList<ItemModel>>()
    val itemList : LiveData<MutableList<ItemModel>> = _itemList

    fun getData() {
        val list = ArrayList<ItemModel>()
        list.apply {
            add(ItemModel("1.초코에몽", 2000))
            add(ItemModel("2.마늘맛초코에몽", 2300))
            add(ItemModel("3.딸기초코에몽", 2500))
            add(ItemModel("4.바나나우유", 1500))
            add(ItemModel("5.딸기우유", 1500))
            add(ItemModel("6.초코우유", 1600))
            add(ItemModel("7.코크", 500))
            add(ItemModel("8.초코에몽", 2000))
            add(ItemModel("9.초코에몽", 2000))
            add(ItemModel("10.초코에몽", 2000))
        }
        _itemList.value = list
    }
}