package com.hs.workation.core.model.dto

data class CreateVehicleControlHistory(
    var vehicleId: String? = null,
    var type: String? = null,
    var result: String? = null,
    var reason: String? = null
)