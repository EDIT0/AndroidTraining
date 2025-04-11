package com.hs.workation.core.model.dto

data class CreateServiceTerm(
    var termId: String? = null,
    var beforeServiceTermId: String? = null,
    var revision: String? = null,
    var description: String? = null,
    var status: String? = null,
    var effectiveStartDate: String? = null
)