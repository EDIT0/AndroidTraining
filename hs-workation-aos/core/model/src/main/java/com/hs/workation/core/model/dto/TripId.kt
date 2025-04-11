package com.hs.workation.core.model.dto

data class TripId(
    var id: String? = null,
    var tripName: String? = null,
    var tripNumber: String? = null,
    var categoryId: String? = null,
    var tripTime: String? = null,
    var description: String? = null,
    var status: String? = null,
    var waypointCount: Int? = null,
    var linkedWaypoints: List<LinkedWaypoint>? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var originId: String? = null
) {
    data class LinkedWaypoint(
        var id: String? = null,
        var tripId: String? = null,
        var waypointId: String? = null,
        var time: String? = null,
        var index: Int? = null
    )
}