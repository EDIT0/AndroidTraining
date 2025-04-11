package com.hs.workation.core.model.dto

data class ShuttleFarePlanHistory(
    var id: String? = null,
    var shuttleFarePlanId: String? = null,
    var workcation: Workcation? = null,
    var version: String? = null,
    var name: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var categories: List<UpdateCategory>? = null,
    var shuttleDistanceFare: PlanShuttleDistanceFare? = null,
    var remark: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
) {
    data class Workcation(
        var id: String? = null,
        var name: String? = null,
        var uri: String? = null,
        var status: String? = null,
        var phoneNumber: String? = null,
        var addressLine1: String? = null,
        var addressLine2: String? = null,
        var city: String? = null,
        var state: String? = null,
        var country: String? = null,
        var zipCode: String? = null
    )
}