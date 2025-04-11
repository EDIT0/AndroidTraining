package com.hs.workation.core.model.dto

data class ShuttleWaypointHistory(
    var id: String? = null,
    var shuttleId: String? = null,
    var scheduleId: String? = null,
    var waypointId: String? = null,
    var status: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)