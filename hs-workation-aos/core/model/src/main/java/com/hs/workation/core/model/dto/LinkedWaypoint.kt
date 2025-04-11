package com.hs.workation.core.model.dto

data class LinkedWaypoint(
    var id: String? = null,
    var tripId: String? = null,
    var waypoint: String? = null,
    var time: String? = null,
    var index: Int? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null
)