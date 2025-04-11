package com.hs.workation.core.model.dto

data class CreateShuttle(
    var numberPlate: String? = null,
    var model: String? = null,
    var displayName: String? = null,
    var status: String? = null,
    var segment: String? = null,
    var fuelType: String? = null,
    var description: String? = null,
    var remark: String? = null,
    var seats: Int? = null,
    var waypointId: String? = null,
    var categoryIds: List<CategoryId>? = null,
    var memos: List<Memo>? = null,
    var fixtureIds: List<FixtureId>? = null,
    var files: List<File>? = null
) {
    data class CategoryId(
        var id: String? = null
    )
    data class Memo(
        var type: String? = null,
        var description: String? = null
    )
    data class FixtureId(
        var id: String? = null
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