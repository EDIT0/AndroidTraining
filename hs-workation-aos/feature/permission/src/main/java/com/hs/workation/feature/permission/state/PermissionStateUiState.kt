package com.hs.workation.feature.permission.state

data class PermissionStateUiState(
    var isState: Boolean? = null,
    var isRetryAvailable: Boolean? = null,
    var rejectedPermissions: ArrayList<String> = arrayListOf()
)
