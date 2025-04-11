package com.hs.workation.core.model.dto

data class VehicleTimeFare(
    var id: String? = null,
    var timeUnit: String? = null,
    var time: String? = null,
    var basicFare: Int? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)