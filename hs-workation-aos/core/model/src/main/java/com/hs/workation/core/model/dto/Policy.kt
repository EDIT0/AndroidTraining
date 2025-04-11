package com.hs.workation.core.model.dto

data class Policy(
    var id: String? = null,
    var title: String? = null,
    var content: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)