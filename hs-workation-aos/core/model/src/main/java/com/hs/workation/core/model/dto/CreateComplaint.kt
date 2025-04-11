package com.hs.workation.core.model.dto

data class CreateComplaint(
    var memberId: String? = null,
    var name: String? = null,
    var answer: String? = null,
    var complaintType: String? = null,
    var channel: String? = null,
    var status: String? = null,
    var question: String? = null,
    var primaryComplaintLevel: String? = null,
    var secondaryComplaintLevel: String? = null,
    var files: List<File>? = null
) {
    data class File(
        var file: FileData? = null
    )

    data class FileData(
        var filename: String? = null,
        var extension: String? = null,
        var blob: java.io.File? = null,
        var isAppVisible: Boolean? = null
    )
}