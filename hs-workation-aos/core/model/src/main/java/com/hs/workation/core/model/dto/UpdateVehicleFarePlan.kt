package com.hs.workation.core.model.dto

data class UpdateVehicleFarePlan(
    var workcationId: String? = null,
    var vehicleDistanceFareId: String? = null,
    var vehicleTimeFareId: String? = null,
    var version: String? = null,
    var name: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var remark: String? = null
)