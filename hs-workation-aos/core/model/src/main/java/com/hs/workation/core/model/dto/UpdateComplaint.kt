package com.hs.workation.core.model.dto

data class UpdateComplaint(
    var answer: String? = null,
    var complaintType: String? = null,
    var channel: String? = null,
    var status: String? = null,
    var question: String? = null,
    var primaryComplaintLevel: String? = null,
    var secondaryComplaintLevel: String? = null,
    var categoryId: String? = null,
    var categoryIds: List<CategoryId>? = null
) {
    data class CategoryId(
        var id: String? = null
    )
}