package com.hs.workation.core.model.dto

data class UpdateWorkcationTerm(
    var workcationId: String? = null,
    var name: String? = null,
    var required: String? = null
)