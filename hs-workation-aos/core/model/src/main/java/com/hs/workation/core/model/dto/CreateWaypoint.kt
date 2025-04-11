package com.hs.workation.core.model.dto

data class CreateWaypoint(
    var name: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var type: String? = null,
    var status: String? = null,
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var zipCode: String? = null,
    var categoryIds: List<CategoryId>? = null,
    var waypointNumber: String? = null,
    var description: String? = null,
    var memos: List<Memo>? = null,
    var files: List<File>? = null
) {
    data class CategoryId(
        var id: String? = null
    )
    data class Memo(
        var type: String? = null,
        var description: String? = null
    )
    data class File(
        var isAppVisible: Boolean? = null,
        var type: String? = null,
        var file: FileData? = null
    )
    data class FileData(
        var extension: String? = null,
        var filename: String? = null,
        var blob: java.io.File? = null
    )
}