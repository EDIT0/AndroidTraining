package com.hs.workation.feature.splash.main.view.viewmodel

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
import com.hs.workation.core.resource.R
import com.hs.workation.core.model.base.SideEffectEvent
import com.hs.workation.core.model.remoteconfig.AppVersion
import com.hs.workation.core.model.remoteconfig.ServerStatus
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.splash.main.event.RemoteConfigUiErrorEvent
import com.hs.workation.feature.splash.main.event.RemoteConfigUiEvent
import com.hs.workation.feature.splash.main.event.SplashStartViewModelEvent
import com.hs.workation.feature.splash.main.state.RemoteConfigUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val scope = viewModelScope

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = if(BuildConfig.DEBUG) {
            0
        } else {
            3600
        }
    }

    private val _sideEffectEvent = Channel<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.receiveAsFlow()

    private val _remoteConfigUiState = MutableStateFlow<RemoteConfigUiEvent>(RemoteConfigUiEvent.Idle)
    val remoteConfigUiState: StateFlow<RemoteConfigUiState> = _remoteConfigUiState.asStateFlow()
        .runningFold(RemoteConfigUiState()) { state, event ->
            when(event) {
                is RemoteConfigUiEvent.Idle -> {
                    state
                }
                is RemoteConfigUiEvent.UpdateRemoteConfigData -> {
                    state.copy(serverStatus = event.serverStatus, appVersion = event.appVersion)
                }
            }
        }.stateIn(scope, SharingStarted.Eagerly, RemoteConfigUiState())

    private val _remoteConfigUiErrorEvent = Channel<RemoteConfigUiErrorEvent>()
    val remoteConfigUiErrorEvent = _remoteConfigUiErrorEvent.receiveAsFlow()

    fun handleViewModelEvent(splashStartViewModelEvent: SplashStartViewModelEvent) {
        when (splashStartViewModelEvent) {
            is SplashStartViewModelEvent.RequestRemoteConfig -> {
                if(!networkManager.checkNetworkState()) {
                    scope.launch {
                        _sideEffectEvent.send(SideEffectEvent.NetworkError(app.getString(R.string.network_connection_check)))
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

                            scope.launch {
                                _remoteConfigUiState.emit(RemoteConfigUiEvent.UpdateRemoteConfigData(serverStatus = serverMaintenance, appVersion = appVersion))
                            }
                        } else {
                            scope.launch {
                                _remoteConfigUiErrorEvent.send(RemoteConfigUiErrorEvent.DataEmpty())
                            }
                        }
                    }
                    .addOnCanceledListener {
                        LogUtil.e_dev("Remote Config Canceled")
                        scope.launch {
                            _remoteConfigUiErrorEvent.send(RemoteConfigUiErrorEvent.Fail(code = REMOTE_CONFIG_FAIL, message = ""))
                        }
                    }
                    .addOnFailureListener { exception ->
                        LogUtil.e_dev("Remote Config Failure ${exception.message}")
                        scope.launch {
                            _remoteConfigUiErrorEvent.send(RemoteConfigUiErrorEvent.Fail(code = REMOTE_CONFIG_EXCEPTION, message = ""))
                        }
                    }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()

        _remoteConfigUiErrorEvent.close()
        _sideEffectEvent.close()
    }
}