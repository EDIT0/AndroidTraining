package com.hs.workation.core.model.dto

data class RoomNonFlatFare(
    var id: String? = null,
    var roomFarePlanId: String? = null,
    var date: String? = null,
    var fare: Int? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)