package com.hs.workation.core.model.dto

data class CalculatedVehicleDistanceFare(
    var distanceUnit: String? = null,
    var distance: Int? = null,
    var originalAmount: Int? = null,
    var savingsAmount: Int? = null,
    var finalAmount: Int? = null
)