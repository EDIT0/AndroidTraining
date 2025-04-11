package com.hs.workation.core.model.dto

data class Assignment(
    var id: String? = null,
    var schedule: String? = null,
    var systemUser: String? = null,
    var status: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)