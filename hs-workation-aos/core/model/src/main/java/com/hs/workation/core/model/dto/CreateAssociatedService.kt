package com.hs.workation.core.model.dto

data class CreateAssociatedService(
    var workcationId: String? = null,
    var companyId: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null
)