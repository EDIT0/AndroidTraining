package com.hs.workation.core.model.dto

data class UpdateRoomFarePlan(
    var workcationId: String? = null,
    var roomNonFlatFareId: String? = null,
    var roomTimeFareId: String? = null,
    var version: String? = null,
    var name: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var remark: String? = null,
    var categoryIds: List<CategoryId>? = null
) {
    data class CategoryId(
        var categoryId: String? = null
    )
}