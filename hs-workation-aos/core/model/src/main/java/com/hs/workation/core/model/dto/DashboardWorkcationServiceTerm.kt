package com.hs.workation.core.model.dto

data class DashboardWorkcationServiceTerm(
    var id: String? = null,
    var workcationId: String? = null,
    var revision: String? = null,
    var required: String? = null,
    var name: String? = null,
    var effectiveStartDate: String? = null,
    var status: String? = null,
    var previousWorkcationServiceTermId: String? = null,
    var previousWorkcationServiceTermRevision: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)