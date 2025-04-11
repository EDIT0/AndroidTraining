package com.hs.workation.core.model.dto

data class CreateVehicleFareDiscountPolicy(
    var vehicleFarePlanId: String? = null,
    var timeUnit: String? = null,
    var elapsedTIme: Int? = null,
    var percent: Int? = null
)