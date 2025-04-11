package com.hs.workation.core.model.dto

data class CreateShuttleTrackingHistory(
    var shuttleId: String? = null,
    var latitued: String? = null,
    var longitude: String? = null
)