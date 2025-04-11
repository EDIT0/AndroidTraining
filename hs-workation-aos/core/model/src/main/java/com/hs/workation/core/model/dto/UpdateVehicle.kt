package com.hs.workation.core.model.dto

data class UpdateVehicle(
    var rentalZoneId: String? = null,
    var terminalId: String? = null,
    var numberPlate: String? = null,
    var model: String? = null,
    var displayName: String? = null,
    var status: String? = null,
    var segment: String? = null,
    var fuelType: String? = null,
    var description: String? = null,
    var age: Int? = null,
    var remark: String? = null,
    var seats: String? = null
)