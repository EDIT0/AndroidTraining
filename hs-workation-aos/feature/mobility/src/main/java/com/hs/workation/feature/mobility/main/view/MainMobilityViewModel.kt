package com.hs.workation.feature.mobility.main.view

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMobilityViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope

}