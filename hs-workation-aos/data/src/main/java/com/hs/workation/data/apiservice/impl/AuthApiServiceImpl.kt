package com.hs.workation.data.apiservice.impl

import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.core.model.test.req.ReqLogin
import com.hs.workation.data.apiservice.AuthApiService
import retrofit2.Response
import javax.inject.Inject

class AuthApiServiceImpl @Inject constructor(
    private val authApiService: AuthApiService
): AuthApiService {
    override suspend fun postRequestLogin(
        reqLogin: ReqLogin
    ): Response<Jwt> {
        return authApiService.postRequestLogin(reqLogin)
    }
}