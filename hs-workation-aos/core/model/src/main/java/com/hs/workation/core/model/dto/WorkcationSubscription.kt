package com.hs.workation.core.model.dto

data class WorkcationSubscription(
    var workcationId: String? = null,
    var memberId: String? = null,
    var status: String? = null,
    var systemUserId: String? = null,
    var applyDate: String? = null,
    var createdBy: String? = null,
    var createdDate: String? = null,
    var modifiedBy: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)