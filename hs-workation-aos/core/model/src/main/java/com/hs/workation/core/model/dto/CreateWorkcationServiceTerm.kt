package com.hs.workation.core.model.dto

data class CreateWorkcationServiceTerm(
    var description: String? = null,
    var status: String? = null,
    var effectiveStartDate: String? = null,
    var workcationTermId: String? = null
)