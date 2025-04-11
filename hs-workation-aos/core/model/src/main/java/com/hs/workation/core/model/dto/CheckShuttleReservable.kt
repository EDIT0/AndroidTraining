package com.hs.workation.core.model.dto

data class CheckShuttleReservable(
    var scheduleId: String? = null,
    var waypointIds: String? = null,
    var isUpdate: Boolean? = null,
    var reservationIdToUpdate: String? = null
)