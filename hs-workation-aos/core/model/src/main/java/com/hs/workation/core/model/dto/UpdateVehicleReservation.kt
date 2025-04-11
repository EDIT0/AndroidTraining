package com.hs.workation.core.model.dto

data class UpdateVehicleReservation(
    var memberId: String? = null,
    var vehicleId: String? = null,
    var secondaryDriverId: String? = null,
    var rentalZoneId: String? = null,
    var returnZoneId: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var reason: String? = null,
    var remark: String? = null,
    var status: String? = null,
    var categoryId: String? = null
)