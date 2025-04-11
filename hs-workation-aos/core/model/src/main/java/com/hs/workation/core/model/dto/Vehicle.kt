package com.hs.workation.core.model.dto

data class Vehicle(
    var id: String? = null,
    var rentalZoneId: String? = null,
    var numberPlate: String? = null,
    var model: String? = null,
    var displayName: String? = null,
    var status: String? = null,
    var segment: String? = null,
    var fuelType: String? = null,
    var description: String? = null,
    var age: Int? = null,
    var remark: String? = null,
    var categories: List<UpdateCategory>? = null,
    var seats: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var terminalId: String? = null
)