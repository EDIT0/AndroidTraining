package com.hs.workation.core.model.dto

data class ShuttleTrackingHistory(
    var id: String? = null,
    var shuttleId: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)