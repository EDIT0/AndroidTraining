package com.hs.workation.core.model.dto

data class UpdateInformationReportHistory(
    var handler: String? = null,
    var requester: String? = null,
    var vehicleId: String? = null,
    var numberPlate: String? = null,
    var memberId: String? = null,
    var name: String? = null,
    var receiver: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var description: String? = null,
    var categoryIds: List<CategoryId>? = null
) {
    data class CategoryId(
        var id: String? = null
    )
}