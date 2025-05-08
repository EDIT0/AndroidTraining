package com.hs.workation.core.model.dto.req

data class ReqSignUp(
    val phoneNumber: String? = null,
    val password: String? = null,
    val email: String? = null,
    val termIDs: List<TermId>? = null,
    val birthday: String? = null,
    val name: String? = null,
    val sex: String? = null
) {
    data class TermId(
        val termId: String? = null
    )
}