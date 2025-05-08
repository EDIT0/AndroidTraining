package com.hs.workation.data.apiservice.impl

import com.hs.workation.core.model.dto.req.ReqLogin
import com.hs.workation.core.model.dto.res.ResLogin
import com.hs.workation.core.model.dto.res.ResLogout
import com.hs.workation.data.apiservice.AuthApiService
import retrofit2.Response
import javax.inject.Inject

class AuthApiServiceImpl @Inject constructor(
    private val authApiService: AuthApiService
): AuthApiService {
    override suspend fun postRequestLogin(
        reqLogin: ReqLogin
    ): Response<ResLogin> {
        return authApiService.postRequestLogin(reqLogin)
    }

    override suspend fun postRequestLogout(
        token: String
    ): Response<ResLogout> {
        return authApiService.postRequestLogout(token)
    }
}