package com.hs.workation.feature.permission.main.event

sealed interface PermissionStateUiEvent {
    object Idle : PermissionStateUiEvent
    class UpdateState(val isState: Boolean, val isRetryAvailable: Boolean, val rejectedPermissions: ArrayList<String>):
        PermissionStateUiEvent
}

sealed interface PermissionStateNoStateUiEvent {

}