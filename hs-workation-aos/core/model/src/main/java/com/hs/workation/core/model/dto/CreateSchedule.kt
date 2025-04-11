package com.hs.workation.core.model.dto

data class CreateSchedule(
    var tripId: String? = null,
    var shuttleId: String? = null,
    var status: String? = null,
    var departureTime: String? = null,
    var scheduleDate: String? = null,
    var description: String? = null
)