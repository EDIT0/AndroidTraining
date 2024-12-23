package com.hs.workation.core.model.remoteconfig

data class ServerStatus(
    val endedAt: String? = null,
    val isMaintenance: Boolean? = null,
    val message: String? = null,
    val startedAt: String? = null
)