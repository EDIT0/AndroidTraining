package com.hs.workation.core.model.dto

data class UpdateServiceSubscription(
    var id: String? = null,
    var status: String? = null,
    var systemUserId: String? = null,
    var applyDate: String? = null
)