package com.hs.workation.feature.permission.event

import android.app.Activity
import com.hs.workation.feature.permission.view.activity.PermissionActivity

sealed interface PermissionViewModelEvent {
    class SettingNavigation(val activity: PermissionActivity): PermissionViewModelEvent
}

sealed interface PermissionCheckViewModelEvent {
    class InitPermissionManager(val activity: PermissionActivity): PermissionCheckViewModelEvent
    class RequestPermission: PermissionCheckViewModelEvent
    class NavigateToPermissionSettingPage: PermissionCheckViewModelEvent
}