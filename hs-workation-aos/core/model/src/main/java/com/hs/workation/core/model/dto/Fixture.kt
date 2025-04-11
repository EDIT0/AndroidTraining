package com.hs.workation.core.model.dto

data class Fixture(
    var id: String? = null,
    var type: String? = null,
    var name: String? = null,
    var status: String? = null,
    var minimumCount: String? = null,
    var recommendCount: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)