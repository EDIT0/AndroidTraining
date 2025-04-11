package com.hs.workation.core.model.dto

data class UpdateVehicleUsage(
    var id: String? = null,
    var reservationId: String? = null,
    var vehicleId: String? = null,
    var memberId: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var categoryId: String? = null
)