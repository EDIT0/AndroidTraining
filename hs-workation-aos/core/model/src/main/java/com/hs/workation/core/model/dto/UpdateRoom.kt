package com.hs.workation.core.model.dto

data class UpdateRoom(
    var accommodationId: String? = null,
    var roomNumber: String? = null,
    var roomName: String? = null,
    var numberGuests: String? = null,
    var maximumCapacity: String? = null,
    var description: String? = null,
    var status: String? = null,
    var categoryIds: List<CategoryId>? = null
) {
    data class CategoryId(
        var id: String? = null
    )
}