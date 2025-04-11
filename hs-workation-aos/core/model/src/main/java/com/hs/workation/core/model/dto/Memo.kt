package com.hs.workation.core.model.dto

data class Memo(
    var id: String? = null,
    var targetId: String? = null,
    var type: String? = null,
    var description: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)