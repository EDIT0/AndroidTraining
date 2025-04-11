package com.hs.workation.core.model.dto

data class ShuttleReservableResult(
    var result: Boolean? = null,
    var seats: Int? = null,
    var linkedWaypoint: String? = null
)