package com.hs.workation.core.model.dto

data class VehicleControlHistory(
    var id: String? = null,
    var vehicleId: String? = null,
    var type: String? = null,
    var result: Boolean? = null,
    var reason: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null,
    var latitude: String? = null,
    var longitude: String? = null
)