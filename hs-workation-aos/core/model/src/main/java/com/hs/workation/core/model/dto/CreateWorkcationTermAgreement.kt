package com.hs.workation.core.model.dto

data class CreateWorkcationTermAgreement(
    var workcationTermId: String? = null,
    var memberId: String? = null,
    var isAgree: Boolean? = null
)