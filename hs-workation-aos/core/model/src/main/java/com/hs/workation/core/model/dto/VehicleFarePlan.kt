package com.hs.workation.core.model.dto

data class VehicleFarePlan(
    var id: String? = null,
    var workcation: Workcation? = null,
    var vehicleDistanceFare: PlanDistanceFare? = null,
    var vehicleTimeFare: PlanTimeFare? = null,
    var vehicleFareDiscountPolicies: PlanDiscountPolicy? = null,
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

    data class PlanDistanceFare(
        var id: String? = null,
        var distanceUnit: String? = null,
        var distance: Int? = null,
        var basicFare: Int? = null
    )

    data class PlanTimeFare(
        var id: String? = null,
        var timeUnit: String? = null,
        var time: String? = null,
        var basicFare: Int? = null
    )

    data class PlanDiscountPolicy(
        var id: String? = null,
        var vehicleFarePlanId: String? = null,
        var timeUnit: String? = null,
        var elapsedTIme: Int? = null,
        var percent: Int? = null
    )
}