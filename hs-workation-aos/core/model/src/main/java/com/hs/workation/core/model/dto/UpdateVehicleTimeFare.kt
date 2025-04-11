package com.hs.workation.core.model.dto

data class UpdateVehicleTimeFare(
    var timeUnit: String? = null,
    var time: Int? = null,
    var basicFare: Int? = null
)