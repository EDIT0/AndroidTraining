package com.hs.workation.core.model.dto

data class CreateRoomFarePlan(
    var workcationId: String? = null,
    var roomNonFlatFareIds: List<RoomFare>? = null,
    var roomFareDiscountPolicies: List<RoomDiscountPolicy>? = null,
    var categoryIds: List<CategoryId>? = null,
    var roomTimeFare: RoomTimeFare? = null,
    var version: String? = null,
    var name: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var remark: String? = null
) {
    data class RoomFare(
        var date: String? = null,
        var fare: Int? = null
    )

    data class RoomDiscountPolicy(
        var timeUnit: String? = null,
        var elapsedTIme: Int? = null,
        var percent: String? = null
    )

    data class CategoryId(
        var categoryId: String? = null
    )

    data class RoomTimeFare(
        var timeUnit: String? = null,
        var time: String? = null,
        var basicFare: Int? = null
    )
}