package com.hs.workation.core.model.dto

data class UpdateSchedule(
    var tripId: String? = null,
    var shuttleId: String? = null,
    var status: String? = null,
    var departureTime: String? = null,
    var arrivalTime: String? = null,
    var scheduleDate: String? = null,
    var systemUserId: String? = null,
    var description: String? = null
)