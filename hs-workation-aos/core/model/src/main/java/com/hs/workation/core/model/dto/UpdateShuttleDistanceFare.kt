package com.hs.workation.core.model.dto

data class UpdateShuttleDistanceFare(
    var distanceUnit: String? = null,
    var distance: Int? = null,
    var basicFare: Int? = null,
    var extraDistanceUnit: String? = null,
    var extraDistance: Int? = null,
    var extrabasicFare: Int? = null
)