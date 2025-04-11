package com.hs.workation.core.model.dto

data class CreateInformationReportHistory(
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
    var isAppVisible: Boolean? = null,
    var file: File? = null,
    var categoryIds: List<CategoryId>? = null
) {
    data class File(
        var type: String? = null,
        var filename: String? = null,
        var extension: String? = null,
        var blob: java.io.File? = null
    )

    data class CategoryId(
        var id: String? = null
    )
}