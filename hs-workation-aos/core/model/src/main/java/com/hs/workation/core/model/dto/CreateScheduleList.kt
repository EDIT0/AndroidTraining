package com.hs.workation.core.model.dto

data class CreateScheduleList(
    var tripId: String? = null,
    var shuttleId: String? = null,
    var dayOfWeeks: List<DayOfWeek>? = null,
    var departureTimes: List<DepartureTime>? = null,
    var from: String? = null,
    var to: String? = null
) {
    data class DayOfWeek(
        var dayOfWeek: String? = null
    )
    data class DepartureTime(
        var departureTime: String? = null
    )
}