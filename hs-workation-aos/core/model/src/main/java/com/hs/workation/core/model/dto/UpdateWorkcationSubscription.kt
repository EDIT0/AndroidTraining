package com.hs.workation.core.model.dto

data class UpdateWorkcationSubscription(
    var status: String? = null,
    var systemUserId: String? = null,
    var applyDate: String? = null
)