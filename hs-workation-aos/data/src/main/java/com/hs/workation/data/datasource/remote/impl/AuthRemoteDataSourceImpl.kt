package com.hs.workation.data.datasource.remote.impl

import com.hs.workation.core.model.dto.req.ReqLogin
import com.hs.workation.core.model.dto.res.ResLogin
import com.hs.workation.core.model.dto.res.ResLogout
import com.hs.workation.data.apiservice.AuthApiService
import com.hs.workation.data.datasource.remote.AuthRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
): AuthRemoteDataSource {
    override suspend fun postRequestLogin(reqLogin: ReqLogin): Response<ResLogin> {
        return authApiService.postRequestLogin(reqLogin)
    }

    override suspend fun postRequestLogout(token: String): Response<ResLogout> {
        return authApiService.postRequestLogout(token)
    }

}