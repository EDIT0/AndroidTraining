package com.hs.workation.core.model.dto

data class Shuttle(
    var id: String? = null,
    var numberPlate: String? = null,
    var model: String? = null,
    var displayName: String? = null,
    var status: String? = null,
    var waypointId: String? = null,
    var segment: String? = null,
    var fuelType: String? = null,
    var description: String? = null,
    var remark: String? = null,
    var seats: Int? = null,
    var categories: List<UpdateCategory>? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)