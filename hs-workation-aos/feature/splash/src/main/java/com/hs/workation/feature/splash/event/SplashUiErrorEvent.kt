package com.hs.workation.feature.splash.event

sealed interface RemoteConfigUiErrorEvent {
    class Init : RemoteConfigUiErrorEvent
    class Fail(val code: String, val message: String?) : RemoteConfigUiErrorEvent
    class ExceptionHandle(val throwable: Throwable) : RemoteConfigUiErrorEvent
    class DataEmpty : RemoteConfigUiErrorEvent
    class ConnectionError(val code: String, val message: String?) : RemoteConfigUiErrorEvent
}

sealed interface Test1UiErrorEvent {
    class Init : Test1UiErrorEvent
    class Fail(val code: String, val message: String?) : Test1UiErrorEvent
    class ExceptionHandle(val throwable: Throwable) : Test1UiErrorEvent
    class DataEmpty : Test1UiErrorEvent
    class ConnectionError(val code: String, val message: String?) : Test1UiErrorEvent
}