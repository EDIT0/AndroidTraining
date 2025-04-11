package com.hs.workation.core.model.dto

data class FarePlanTarget(
    var farePlanTargetId: String? = null,
    var farePlanId: String? = null,
    var version: String? = null,
    var status: String? = null,
    var farePlan: FarePlan? = null,
    var category: UpdateCategory? = null,
    var description: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
) {
    data class FarePlan(
        var id: String? = null,
        var workcationId: String? = null,
        var version: String? = null,
        var name: String? = null,
        var status: String? = null,
        var startDate: String? = null,
        var endDate: String? = null,
        var remark: String? = null,
        var categoryId: String? = null
    )
}