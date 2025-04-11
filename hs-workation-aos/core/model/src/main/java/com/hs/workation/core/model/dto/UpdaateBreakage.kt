package com.hs.workation.core.model.dto

data class UpdaateBreakage(
    var description: String? = null,
    var systemUserId: String? = null,
    var status: String? = null,
    var reservationId: String? = null,
    var memberId: String? = null
)