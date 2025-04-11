package com.hs.workation.core.model.dto

data class CreateShuttleWaypointHistory(
    var shuttleId: String? = null,
    var scheduleId: String? = null,
    var waypointId: String? = null,
    var status: String? = null
)