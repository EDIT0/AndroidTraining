package com.hs.workation.core.model.dto

data class Trip(
    var id: String? = null,
    var tripName: String? = null,
    var tripNumber: String? = null,
    var categoryId: String? = null,
    var tripTime: Int? = null,
    var maxTripTime: Int? = null,
    var description: String? = null,
    var status: String? = null,
    var waypointCount: Int? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var originId: String? = null
)