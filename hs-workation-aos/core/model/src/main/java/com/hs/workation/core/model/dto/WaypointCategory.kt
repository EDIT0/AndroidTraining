package com.hs.workation.core.model.dto

data class WaypointCategory(
    var id: String? = null,
    var waypointId: String? = null,
    var categoryId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)