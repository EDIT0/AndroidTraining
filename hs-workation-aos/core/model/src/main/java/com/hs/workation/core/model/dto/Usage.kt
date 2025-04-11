package com.hs.workation.core.model.dto

data class Usage(
    var id: String? = null,
    var reservationId: String? = null,
    var targetId: String? = null,
    var memberId: String? = null,
    var status: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var category: UpdateCategory? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)