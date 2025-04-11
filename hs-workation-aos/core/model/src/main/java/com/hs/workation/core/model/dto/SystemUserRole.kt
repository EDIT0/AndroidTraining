package com.hs.workation.core.model.dto

data class SystemUserRole(
    var id: String? = null,
    var systemUserId: String? = null,
    var roleId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)