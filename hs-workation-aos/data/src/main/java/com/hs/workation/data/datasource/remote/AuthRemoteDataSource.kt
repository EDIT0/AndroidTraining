package com.hs.workation.data.datasource.remote

import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.core.model.test.req.ReqLogin
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun postRequestLogin(reqLogin: ReqLogin): Response<Jwt>
}