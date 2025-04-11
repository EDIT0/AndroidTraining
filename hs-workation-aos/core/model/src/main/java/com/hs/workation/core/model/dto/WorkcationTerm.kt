package com.hs.workation.core.model.dto

data class WorkcationTerm(
    var id: String? = null,
    var workcationId: String? = null,
    var name: String? = null,
    var required: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)