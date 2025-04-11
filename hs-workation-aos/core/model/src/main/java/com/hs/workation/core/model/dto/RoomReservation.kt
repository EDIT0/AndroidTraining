package com.hs.workation.core.model.dto

data class RoomReservation(
    var id: String? = null,
    var memberId: String? = null,
    var room: Room? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var status: String? = null,
    var reason: String? = null,
    var remark: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var categoryId: String? = null,
    var numberGuests: Int? = null,
    var workcationId: String? = null
)