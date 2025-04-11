package com.hs.workation.core.model.dto

data class UpdateVehicleDistanceFare(
    var distanceUnit: String? = null,
    var distance: Int? = null,
    var basicFare: Int? = null
)