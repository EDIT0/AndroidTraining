package com.hs.workation.feature.splash.state

import androidx.paging.PagingData
import com.hs.workation.core.model.remoteconfig.AppVersion
import com.hs.workation.core.model.remoteconfig.ServerStatus
import com.hs.workation.domain.model.res.ResTest1
import com.hs.workation.domain.model.res.ResTest2

data class RemoteConfigUiState(
    val serverStatus: ServerStatus? = null,
    val appVersion: AppVersion? = null
)

data class Test1UiState(
    val resTest1: ResTest1? = null,
    val resTest2: ResTest2? = null,
    val questions: PagingData<ResTest2.TestQuestion>? = null
)