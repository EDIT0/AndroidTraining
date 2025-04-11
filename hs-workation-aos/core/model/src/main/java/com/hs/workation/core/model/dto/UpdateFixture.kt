package com.hs.workation.core.model.dto

data class UpdateFixture(
    var type: String? = null,
    var name: String? = null,
    var status: String? = null,
    var minimumCount: String? = null,
    var recommendCount: String? = null
)