package com.hs.workation.feature.splash.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.google.gson.Gson
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.common.constants.RemoteConfigConstants
import com.hs.workation.core.common.constants.RemoteConfigConstants.REMOTE_CONFIG_EXCEPTION
import com.hs.workation.core.common.constants.RemoteConfigConstants.REMOTE_CONFIG_FAIL
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.splash.BuildConfig
import com.hs.workation.core.common.R
import com.hs.workation.core.model.remoteconfig.AppVersion
import com.hs.workation.core.model.remoteconfig.ServerStatus
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.splash.event.RemoteConfigUiErrorEvent
import com.hs.workation.feature.splash.event.RemoteConfigUiEvent
import com.hs.workation.feature.splash.event.SplashStartViewModelEvent
import com.hs.workation.feature.splash.state.RemoteConfigUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashStartViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
) : BaseAndroidViewModel(app, networkManager) {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = if(BuildConfig.DEBUG) {
            0
        } else {
            3600
        }
    }

    private val _sideEffectEvent = MutableSharedFlow<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.asSharedFlow()

    private val _remoteConfigUiState = Channel<RemoteConfigUiEvent>()
    val remoteConfigUiState: StateFlow<RemoteConfigUiState> = _remoteConfigUiState.receiveAsFlow()
        .runningFold(RemoteConfigUiState()) { state, event ->
            when(event) {
                is RemoteConfigUiEvent.UpdateRemoteConfigData -> {
                    state.copy(serverStatus = event.serverStatus, appVersion = event.appVersion)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, RemoteConfigUiState())

    private val _remoteConfigUiErrorEvent = Channel<RemoteConfigUiErrorEvent>()
    val remoteConfigUiErrorEvent = _remoteConfigUiErrorEvent.receiveAsFlow()

    fun handleViewModelEvent(splashStartViewModelEvent: SplashStartViewModelEvent) {
        when (splashStartViewModelEvent) {
            is SplashStartViewModelEvent.RequestRemoteConfig -> {
                if(!networkManager.checkNetworkState()) {
                    viewModelScope.launch {
                        _sideEffectEvent.emit(SideEffectEvent.NetworkError(app.getString(R.string.network_connection_check)))
                    }
                    return
                }
                remoteConfig.setConfigSettingsAsync(configSettings)
                remoteConfig.setDefaultsAsync(R.xml.remote_config)
                remoteConfig.fetchAndActivate()
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            val serverStatus = remoteConfig.getString(RemoteConfigConstants.REMOTE_CONFIG_SERVER_STATUS)
                            val workationAppVersion = remoteConfig.getString(RemoteConfigConstants.REMOTE_CONFIG_WORKATION_APP_VERSION)

                            val serverMaintenance = Gson().fromJson(serverStatus, ServerStatus::class.java)
                            val appVersion = Gson().fromJson(workationAppVersion, AppVersion::class.java)

                            LogUtil.d_dev("Remote Config Complete \n${serverStatus}\n${workationAppVersion}")
                            LogUtil.d_dev("Remote Config Complete \n${serverMaintenance}\n${appVersion}")

                            viewModelScope.launch {
                                _remoteConfigUiState.send(RemoteConfigUiEvent.UpdateRemoteConfigData(serverStatus = serverMaintenance, appVersion = appVersion))
                            }
                        } else {
                            viewModelScope.launch {
                                _remoteConfigUiErrorEvent.send(RemoteConfigUiErrorEvent.DataEmpty())
                            }
                        }
                    }
                    .addOnCanceledListener {
                        LogUtil.e_dev("Remote Config Canceled")
                        viewModelScope.launch {
                            _remoteConfigUiErrorEvent.send(RemoteConfigUiErrorEvent.Fail(code = REMOTE_CONFIG_FAIL, message = ""))
                        }
                    }
                    .addOnFailureListener { exception ->
                        LogUtil.e_dev("Remote Config Failure ${exception.message}")
                        viewModelScope.launch {
                            _remoteConfigUiErrorEvent.send(RemoteConfigUiErrorEvent.Fail(code = REMOTE_CONFIG_EXCEPTION, message = ""))
                        }
                    }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()

        _remoteConfigUiState.close()
        _remoteConfigUiErrorEvent.close()
    }

    sealed class SideEffectEvent {
        class NetworkError(val message: String): SideEffectEvent()
    }
}