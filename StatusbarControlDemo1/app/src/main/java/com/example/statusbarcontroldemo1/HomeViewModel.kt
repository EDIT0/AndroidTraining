package com.example.statusbarcontroldemo1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    private val _txtState = MutableStateFlow<String>(LONG)
    val txtState = _txtState.asStateFlow()

    init {
        setTxtState(LONG)
    }

    fun setTxtState(state: String) {
        viewModelScope.launch {
            when(state) {
                LONG -> {
                    _txtState.emit(app.getString(R.string.long_text))
                }
                SHORT -> {
                    _txtState.emit(app.getString(R.string.short_text))
                }
                LONG_2 -> {
                    _txtState.emit(app.getString(R.string.long_text_2))
                }
            }
        }
    }

    companion object {
        const val LONG = "LONG"
        const val SHORT = "SHORT"
        const val LONG_2 = "LONG_2"
    }
}