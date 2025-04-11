package com.hs.workation.core.model.dto

data class VehicleFarePlanTarget(
    var id: String? = null,
    var vehicleFarePlanId: String? = null,
    var category: UpdateCategory? = null,
    var status: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)