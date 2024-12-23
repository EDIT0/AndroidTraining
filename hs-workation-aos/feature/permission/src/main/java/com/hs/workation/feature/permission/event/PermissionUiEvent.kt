package com.hs.workation.feature.permission.event

sealed interface PermissionStateUiEvent {
    class UpdateState(val isState: Boolean, val isRetryAvailable: Boolean, val rejectedPermissions: ArrayList<String>): PermissionStateUiEvent
}

sealed interface PermissionStateNoStateUiEvent {

}