package com.hs.workation.core.model.dto

data class ShuttleUsage(
    var id: String? = null,
    var reservationId: String? = null,
    var shuttleId: String? = null,
    var memberId: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null,
    var category: UpdateCategory? = null,
    var startWaypointId: String? = null,
    var endWaypointId: String? = null
)