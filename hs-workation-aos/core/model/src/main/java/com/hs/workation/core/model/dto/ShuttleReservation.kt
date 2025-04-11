package com.hs.workation.core.model.dto

data class ShuttleReservation(
    var id: String? = null,
    var memberId: String? = null,
    var srcWaypointId: String? = null,
    var dstWaypointId: String? = null,
    var tripId: TripId? = null,
    var shuttle: Shuttle? = null,
    var scheduleId: String? = null,
    var assignmentId: String? = null,
    var customers: Int? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var reason: String? = null,
    var remark: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var categoryID: String? = null,
    var workcationId: String? = null
)