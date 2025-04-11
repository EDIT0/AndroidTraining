package com.hs.workation.core.model.dto

data class CreateShuttleUsage(
    var reservationId: String? = null,
    var shuttleId: String? = null,
    var memberId: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var categoryId: String? = null,
    var startWaypointId: String? = null,
    var endWaypointId: String? = null
)