package com.hs.workation.feature.splash.main.event

import com.hs.workation.core.model.remoteconfig.AppVersion
import com.hs.workation.core.model.remoteconfig.ServerStatus

sealed interface RemoteConfigUiEvent {
    object Idle : RemoteConfigUiEvent
    class UpdateRemoteConfigData(val serverStatus: ServerStatus, val appVersion: AppVersion): RemoteConfigUiEvent
}

sealed interface SplashStartNoStateUiEvent {

}