package com.hs.workation.core.model.dto

data class CreateRoomUsage(
    var reservationId: String? = null,
    var roomId: String? = null,
    var memberId: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var categoryId: String? = null
)