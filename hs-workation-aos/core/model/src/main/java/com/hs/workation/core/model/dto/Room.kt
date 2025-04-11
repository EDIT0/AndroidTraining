package com.hs.workation.core.model.dto

data class Room(
    var id: String? = null,
    var accommodationId: String? = null,
    var categories: List<UpdateCategory>? = null,
    var roomNumber: String? = null,
    var roomName: String? = null,
    var numberGuests: String? = null,
    var maximumCapacity: String? = null,
    var description: String? = null,
    var status: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)