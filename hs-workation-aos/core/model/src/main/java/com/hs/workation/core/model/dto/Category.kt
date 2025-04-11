package com.hs.workation.core.model.dto

data class Category(
    var id: String? = null,
    var primaryServiceLevel: String? = null,
    var secondaryServiceLevel: String? = null,
    var associatedServiceId: String? = null,
    var workcationId: String? = null,
    var name: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
)