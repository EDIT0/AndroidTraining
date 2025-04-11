package com.hs.workation.core.model.dto

data class ShuttleDistanceFare(
    var id: String? = null,
    var distanceUnit: String? = null,
    var distance: Int? = null,
    var basicFare: Int? = null,
    var extraDistanceUnit: String? = null,
    var extraDistance: Int? = null,
    var extrabasicFare: Int? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)