package com.hs.workation.core.model.dto.res

data class ResLogin(
    var access_token: String? = null,
    var refresh_token: String? = null,
    var scope: String? = null,
    var id_token: String? = null,
    var token_type: String? = null,
    var expires_in: Int? = null
)