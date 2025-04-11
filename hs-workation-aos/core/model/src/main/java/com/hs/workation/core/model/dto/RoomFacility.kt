package com.hs.workation.core.model.dto

data class RoomFacility(
    var facilityId: String? = null,
    var roomId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)