package com.hs.workation.feature.login.event

import com.hs.workation.feature.login.view.activity.LoginActivity

sealed interface LoginViewModelEvent {
    class SettingNavigation(val activity: LoginActivity): LoginViewModelEvent
}