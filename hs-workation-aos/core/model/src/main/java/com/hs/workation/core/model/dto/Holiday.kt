package com.hs.workation.core.model.dto

data class Holiday(
    var id: String? = null,
    var entryYear: String? = null,
    var entryDate: String? = null,
    var systemUserName: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)