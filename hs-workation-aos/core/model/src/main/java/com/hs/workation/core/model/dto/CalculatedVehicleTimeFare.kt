package com.hs.workation.core.model.dto

data class CalculatedVehicleTimeFare(
    var timeUnit: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var originalAmount: Int? = null,
    var savingsAmount: Int? = null,
    var finalAmount: Int? = null
)