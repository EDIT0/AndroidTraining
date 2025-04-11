package com.hs.workation.core.model.dto

data class UpdateShuttleReservation(
    var memberId: String? = null,
    var srcWaypointId: String? = null,
    var dstWaypointId: String? = null,
    var tripId: String? = null,
    var shuttleId: String? = null,
    var scheduleId: String? = null,
    var assignmentId: String? = null,
    var customers: Int? = null,
    var status: String? = null,
    var endDate: String? = null,
    var reason: String? = null,
    var remark: String? = null,
    var categoryId: String? = null
)