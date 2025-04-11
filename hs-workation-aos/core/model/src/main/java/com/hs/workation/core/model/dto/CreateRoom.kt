package com.hs.workation.core.model.dto

data class CreateRoom(
    var accommodationId: String? = null,
    var categoryIds: List<CategoryId>? = null,
    var roomNumber: String? = null,
    var roomName: String? = null,
    var status: String? = null,
    var numberGuests: String? = null,
    var maximumCapacity: String? = null,
    var description: String? = null,
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
        var file: FileData? = null,
    )
    data class FileData(
        var extension: String? = null,
        var filename: String? = null,
        var blob: java.io.File? = null
    )
}