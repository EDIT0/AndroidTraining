package com.hs.workation.data.apiservice.impl

import com.hs.workation.core.model.dto.IdAndPassword
import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.core.model.dto.ServiceResult
import com.hs.workation.data.apiservice.AuthApiService
import retrofit2.Response
import javax.inject.Inject

class AuthApiServiceImpl @Inject constructor(
    private val authApiService: AuthApiService
): AuthApiService {
    override suspend fun postRequestLogin(
        idAndPassword: IdAndPassword
    ): Response<Jwt> {
        return authApiService.postRequestLogin(idAndPassword)
    }

    override suspend fun postRequestLogout(
        token: String
    ): Response<ServiceResult> {
        return authApiService.postRequestLogout(token)
    }
}