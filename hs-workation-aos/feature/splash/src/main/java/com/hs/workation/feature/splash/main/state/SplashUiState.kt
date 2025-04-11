package com.hs.workation.feature.splash.main.state

import com.hs.workation.core.model.remoteconfig.AppVersion
import com.hs.workation.core.model.remoteconfig.ServerStatus

data class RemoteConfigUiState(
    val serverStatus: ServerStatus? = null,
    val appVersion: AppVersion? = null
)