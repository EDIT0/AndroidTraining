package com.example.bottomnavigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigation.event.CenterTextUiEvent
import com.example.bottomnavigation.event.OneViewModelEvent
import com.example.bottomnavigation.state.CenterTextUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OneViewModel : ViewModel() {

    private val _centerTextUiState = MutableStateFlow<CenterTextUiEvent>(CenterTextUiEvent.SuccessChangeCenterText(centerText = "Init One!"))
    val centerTextUiState: StateFlow<CenterTextUiState> = _centerTextUiState.runningFold(CenterTextUiState()) { state, event ->
        when(event) {
            is CenterTextUiEvent.Init -> {
                state
            }
            is CenterTextUiEvent.SuccessChangeCenterText -> {
                state.copy(centerText = event.centerText)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, CenterTextUiState())

    fun handleViewModelEvent(oneViewModelEvent: OneViewModelEvent) {
        when(oneViewModelEvent) {
            is OneViewModelEvent.ChangeCenterText -> {
                viewModelScope.launch {
                    _centerTextUiState.emit(CenterTextUiEvent.SuccessChangeCenterText(centerTextUiState.value.centerText + "${oneViewModelEvent.centerText}"))
                }
            }
        }
    }

//    private var _text = MutableStateFlow<String>("OneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\nOneFragment\n")
//    val text = _text.asStateFlow()
//
//    fun setCenterText(text: String) {
//        _text.value = text
//    }
}