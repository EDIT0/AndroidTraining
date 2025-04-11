package com.hs.workation.core.model.dto

data class DashboardServiceTerm(
    var id: String? = null,
    var revision: String? = null,
    var primaryServiceLevel: String? = null,
    var secondaryServiceLevel: String? = null,
    var associatedServiceName: String? = null,
    var required: String? = null,
    var name: String? = null,
    var effectiveStartDate: String? = null,
    var status: String? = null,
    var previousServiceTermId: String? = null,
    var previousServiceTermRevision: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)