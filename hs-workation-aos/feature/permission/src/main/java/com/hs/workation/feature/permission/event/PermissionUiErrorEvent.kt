package com.hs.workation.feature.permission.event

sealed interface PermissionStateUiErrorEvent {
    class Init : PermissionStateUiErrorEvent
    class Fail(val code: String, val message: String?) : PermissionStateUiErrorEvent
    class ExceptionHandle(val throwable: Throwable) : PermissionStateUiErrorEvent
    class DataEmpty : PermissionStateUiErrorEvent
    class ConnectionError(val code: String, val message: String?) : PermissionStateUiErrorEvent
}