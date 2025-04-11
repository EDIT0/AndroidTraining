package com.hs.workation.core.model.dto

data class WorkcationServiceTerm(
    var Id: String? = null,
    var workcationTermId: String? = null,
    var revision: String? = null,
    var description: String? = null,
    var status: String? = null,
    var effectiveStartDate: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)