package com.hs.workation.core.model.dto

data class CalculateShuttleFareResult(
    var guests: Int? = null,
    var fare: Int? = null,
    var Discount: Int? = null,
    var resultFare: Int? = null
)