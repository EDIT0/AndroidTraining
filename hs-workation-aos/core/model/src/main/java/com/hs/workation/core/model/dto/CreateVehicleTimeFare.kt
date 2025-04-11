package com.hs.workation.core.model.dto

data class CreateVehicleTimeFare(
    var timeUnit: String? = null,
    var time: String? = null,
    var basicFare: Int? = null
)