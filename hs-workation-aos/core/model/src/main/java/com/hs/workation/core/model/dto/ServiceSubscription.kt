package com.hs.workation.core.model.dto

data class ServiceSubscription(
    var id: String? = null,
    var memberId: String? = null,
    var associatedServiceId: String? = null,
    var associatedServiceName: String? = null,
    var status: String? = null,
    var systemUserId: String? = null,
    var applyDate: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)