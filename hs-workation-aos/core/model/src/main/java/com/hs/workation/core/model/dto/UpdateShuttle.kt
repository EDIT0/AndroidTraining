package com.hs.workation.core.model.dto

data class UpdateShuttle(
    var numberPlate: String? = null,
    var model: String? = null,
    var displayName: String? = null,
    var status: String? = null,
    var waypointId: String? = null,
    var primaryServiceLevel: String? = null,
    var secondaryServiceLevel: String? = null,
    var segment: String? = null,
    var fuelType: String? = null,
    var description: String? = null,
    var remark: String? = null,
    var seats: Int? = null
)