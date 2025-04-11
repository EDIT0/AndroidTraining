package com.hs.workation.core.model.dto

data class UpdateWorkcationServiceTerm(
    var revision: String? = null,
    var description: String? = null,
    var status: String? = null,
    var effectiveStartDate: String? = null
)