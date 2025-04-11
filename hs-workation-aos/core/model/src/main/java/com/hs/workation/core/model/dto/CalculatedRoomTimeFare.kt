package com.hs.workation.core.model.dto

data class CalculatedRoomTimeFare(
    var timeUnit: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var guests: String? = null,
    var originalAmount: Int? = null,
    var savingsAmount: Int? = null,
    var finalAmount: Int? = null
)