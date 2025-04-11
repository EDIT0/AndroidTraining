package com.hs.workation.core.model.dto

data class RoomPolicy(
    var id: String? = null,
    var roomId: String? = null,
    var policyId: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)