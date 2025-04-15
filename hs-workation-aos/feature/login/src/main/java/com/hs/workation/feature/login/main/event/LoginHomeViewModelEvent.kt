package com.hs.workation.feature.login.main.event

sealed interface LoginHomeViewModelEvent {
    class RequestLogin(): LoginHomeViewModelEvent
    class RequestLogout(): LoginHomeViewModelEvent
}