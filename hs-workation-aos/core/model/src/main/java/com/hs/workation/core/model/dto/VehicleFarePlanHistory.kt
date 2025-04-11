package com.hs.workation.core.model.dto

data class VehicleFarePlanHistory(
    var id: String? = null,
    var roomFarePlanId: String? = null,
    var workcationId: String? = null,
    var roomTimeFareId: String? = null,
    var version: String? = null,
    var name: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var remark: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var categories: List<UpdateCategory>? = null
)