package com.hs.workation.feature.login.main.event

import com.hs.workation.feature.login.main.view.activity.LoginActivity

sealed interface LoginViewModelEvent {
    class SettingNavigation(val activity: LoginActivity): LoginViewModelEvent
}