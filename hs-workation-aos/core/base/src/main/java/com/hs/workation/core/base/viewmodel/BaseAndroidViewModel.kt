package com.hs.workation.core.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hs.workation.core.util.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseAndroidViewModel @Inject constructor(
    val app: Application,
    val networkManager: NetworkManager,
) : AndroidViewModel(application = app) {

    open fun networkCheck(): Boolean {
        if(!networkManager.checkNetworkState()) {
            return false
        }
        return true
    }
}