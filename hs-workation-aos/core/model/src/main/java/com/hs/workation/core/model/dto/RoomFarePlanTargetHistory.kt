package com.hs.workation.core.model.dto

data class RoomFarePlanTargetHistory(
    var id: String? = null,
    var roomFarePlanTargetId: String? = null,
    var roomFarePlanId: String? = null,
    var categoryId: String? = null,
    var status: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)