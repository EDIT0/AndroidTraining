package com.my.cardocrdemo1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(val app: Application) : AndroidViewModel(app) {

    private var _cardInfoModel = MutableStateFlow<CardInfoModel>(CardInfoModel("", ""))
    val cardInfoModel: StateFlow<CardInfoModel> = _cardInfoModel.asStateFlow()

    fun setCardInfo(cardInfoModel: CardInfoModel) {
        _cardInfoModel.value = _cardInfoModel.value.copy(cardInfoModel.number, cardInfoModel.date)
    }

}