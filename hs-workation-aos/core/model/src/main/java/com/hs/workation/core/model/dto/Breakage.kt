package com.hs.workation.core.model.dto

data class Breakage(
    var id: String? = null,
    var description: String? = null,
    var systemUserId: String? = null,
    var status: String? = null,
    var reservationId: String? = null,
    var memberId: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)