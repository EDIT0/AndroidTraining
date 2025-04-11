package com.hs.workation.core.model.dto

data class UpdateCategory(
    var id: String? = null,
    var primaryServiceLevel: String? = null,
    var secondaryServiceLevel: String? = null,
    var name: String? = null,
    var associatedServiceId: String? = null,
    var workcationId: String? = null
)