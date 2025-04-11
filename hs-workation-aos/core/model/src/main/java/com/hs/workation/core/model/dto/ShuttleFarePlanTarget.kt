package com.hs.workation.core.model.dto

data class ShuttleFarePlanTarget(
    var id: String? = null,
    var shuttleFarePlanId: String? = null,
    var categoryId: String? = null,
    var version: String? = null,
    var status: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)