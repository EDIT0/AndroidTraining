package com.hs.workation.data.apiservice

import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.core.model.test.req.ReqLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login")
    suspend fun postRequestLogin(
        @Body reqLogin: ReqLogin
    ): Response<Jwt>

}