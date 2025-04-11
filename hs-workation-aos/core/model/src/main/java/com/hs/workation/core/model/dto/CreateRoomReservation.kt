package com.hs.workation.core.model.dto

data class CreateRoomReservation(
    var memberId: String? = null,
    var roomId: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var status: String? = null,
    var reason: String? = null,
    var remark: String? = null,
    var categoryId: String? = null,
    var numberGuests: Int? = null,
    var workcationId: String? = null
)