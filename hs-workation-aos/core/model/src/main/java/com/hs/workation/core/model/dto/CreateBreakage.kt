package com.hs.workation.core.model.dto

data class CreateBreakage(
    var description: String? = null,
    var reservationId: String? = null,
    var memberId: String? = null
)