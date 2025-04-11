package com.hs.workation.feature.splash.main.event

sealed interface RemoteConfigUiErrorEvent {
    class Init : RemoteConfigUiErrorEvent
    class Fail(val code: String, val message: String?) : RemoteConfigUiErrorEvent
    class ExceptionHandle(val throwable: Throwable) : RemoteConfigUiErrorEvent
    class DataEmpty : RemoteConfigUiErrorEvent
    class ConnectionError(val code: String, val message: String?) : RemoteConfigUiErrorEvent
}