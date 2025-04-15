package com.hs.workation.core.model.base

interface SideEffectEvent {
    class NetworkError(val message: String): SideEffectEvent
}
