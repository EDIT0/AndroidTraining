package com.hs.workation.feature.splash.event

import androidx.paging.PagingData
import com.hs.workation.core.model.remoteconfig.AppVersion
import com.hs.workation.core.model.remoteconfig.ServerStatus
import com.hs.workation.domain.model.res.ResTest1
import com.hs.workation.domain.model.res.ResTest2

sealed interface RemoteConfigUiEvent {
    class UpdateRemoteConfigData(val serverStatus: ServerStatus, val appVersion: AppVersion): RemoteConfigUiEvent
}

sealed interface SplashStartNoStateUiEvent {

}

// State가 유지 되어야 하는 객체 (UiEvent)
sealed interface Test1UiEvent {
    class UpdateResTest1(val resTest1: ResTest1): Test1UiEvent
    class UpdateResTest2(val resTest2: ResTest2): Test1UiEvent
    class UpdateQuestions(val questions: PagingData<ResTest2.TestQuestion>): Test1UiEvent
}

// State 유지가 필요없는 단발성 이벤트 (NoStateUiEvent)
sealed interface Test1NoStateUiEvent {
    class UpdateIsDeleteListItem(val isDelete: Boolean): Test1NoStateUiEvent
}