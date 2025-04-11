package com.hs.workation.core.model.dto

data class Waypoint(
    var id: String? = null,
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
    var waypointNumber: String? = null,
    var description: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var categories: List<UpdateCategory>? = null
)