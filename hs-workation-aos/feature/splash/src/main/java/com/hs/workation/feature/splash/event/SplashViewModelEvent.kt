package com.hs.workation.feature.splash.event

import com.hs.workation.domain.model.req.ReqTest2
import com.hs.workation.domain.model.res.ResTest2
import com.hs.workation.feature.splash.view.activity.SplashActivity

sealed interface SplashViewModelEvent {
    class SettingNavigation(val activity: SplashActivity): SplashViewModelEvent
}

sealed interface SplashStartViewModelEvent {
    class RequestRemoteConfig(): SplashStartViewModelEvent
}

sealed interface Test1ViewModelEvent {
    class GetTest1(): Test1ViewModelEvent
    class GetTest2(val reqTest2: ReqTest2): Test1ViewModelEvent
    class DeleteTest2Question(val question: ResTest2.TestQuestion): Test1ViewModelEvent
}