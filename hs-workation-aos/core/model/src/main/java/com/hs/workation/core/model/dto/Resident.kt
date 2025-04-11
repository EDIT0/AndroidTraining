package com.hs.workation.core.model.dto

data class Resident(
    var id: String? = null,
    var memberId: String? = null,
    var region: String? = null,
    var status: String? = null,
    var systemUserId: String? = null,
    var applyDate: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)