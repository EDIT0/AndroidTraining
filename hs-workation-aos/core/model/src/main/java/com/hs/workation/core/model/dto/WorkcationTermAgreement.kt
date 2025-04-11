package com.hs.workation.core.model.dto

data class WorkcationTermAgreement(
    var id: String? = null,
    var workcationTermId: String? = null,
    var memberId: String? = null,
    var isAgree: Boolean? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)