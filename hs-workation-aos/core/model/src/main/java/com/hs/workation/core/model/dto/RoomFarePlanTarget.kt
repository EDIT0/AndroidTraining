package com.hs.workation.core.model.dto

data class RoomFarePlanTarget(
    var id: String? = null,
    var roomFarePlanId: String? = null,
    var category: UpdateCategory? = null,
    var status: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)