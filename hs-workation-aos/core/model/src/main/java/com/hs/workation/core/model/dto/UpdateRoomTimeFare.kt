package com.hs.workation.core.model.dto

data class UpdateRoomTimeFare(
    var timeUnit: String? = null,
    var time: String? = null,
    var basicFare: Int? = null
)