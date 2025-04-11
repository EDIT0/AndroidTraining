package com.hs.workation.core.model.dto

data class UpdateShuttleFarePlan(
    var workcationId: String? = null,
    var categoryIds: List<CategoryId>? = null,
    var version: String? = null,
    var name: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var remark: String? = null,
    var shuttleDistanceFare: ShuttleDistanceFare? = null
) {
    data class CategoryId(
        var id: String? = null
    )
    data class ShuttleDistanceFare(
        var distanceUnit: String? = null,
        var distance: Int? = null,
        var basicFare: Int? = null,
        var extraDistanceUnit: String? = null,
        var extraDistance: Int? = null,
        var extrabasicFare: Int? = null
    )
}