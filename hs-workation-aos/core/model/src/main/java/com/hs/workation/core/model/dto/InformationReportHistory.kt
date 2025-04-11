package com.hs.workation.core.model.dto

data class InformationReportHistory(
    var id: String? = null,
    var handler: String? = null,
    var handlerName: String? = null,
    var requester: String? = null,
    var vehicleId: String? = null,
    var numberPlate: String? = null,
    var memberId: String? = null,
    var name: String? = null,
    var receiver: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var description: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null,
    var fileUrl: String? = null,
    var categories: List<UpdateCategory>? = null
)