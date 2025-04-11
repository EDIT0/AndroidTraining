package com.hs.workation.feature.splash.main.event

import com.hs.workation.feature.splash.main.view.activity.SplashActivity

sealed interface SplashViewModelEvent {
    class SettingNavigation(val activity: SplashActivity): SplashViewModelEvent
}

sealed interface SplashStartViewModelEvent {
    class RequestRemoteConfig(): SplashStartViewModelEvent
}