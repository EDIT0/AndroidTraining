package com.hs.workation.core.model.dto

data class VehicleReservation(
    var id: String? = null,
    var memberId: String? = null,
    var vehicle: ReservationVehicle? = null,
    var secondaryDriverId: String? = null,
    var rentalZoneId: String? = null,
    var returnZoneId: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var reason: String? = null,
    var remark: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var categoryId: String? = null,
    var workcationId: String? = null
)