package com.hs.workation.core.model.dto

data class Schedule(
    var id: String? = null,
    var tripId: String? = null,
    var shuttleId: String? = null,
    var status: String? = null,
    var departureTime: String? = null,
    var scheduleDate: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)