package com.hs.workation.core.model.fcm

import java.io.Serializable

data class CommonPayload(
    val nick: String? = null,
    val body: String? = null,
    val room: String? = null
): Serializable
