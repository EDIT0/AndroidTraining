package com.hs.workation.core.model.dto

data class File(
    var id: String? = null,
    var extension: String? = null,
    var filename: String? = null,
    var hash: String? = null,
    var url: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)