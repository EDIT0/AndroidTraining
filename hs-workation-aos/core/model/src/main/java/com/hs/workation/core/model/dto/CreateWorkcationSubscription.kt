package com.hs.workation.core.model.dto

data class CreateWorkcationSubscription(
    var workcationId: String? = null,
    var memberId: String? = null,
    var status: String? = null,
    var systemUserId: String? = null,
    var applyDate: String? = null
)