package com.hs.workation.data.apiservice

import com.hs.workation.core.model.dto.req.ReqLogin
import com.hs.workation.core.model.dto.res.ResLogin
import com.hs.workation.core.model.dto.res.ResLogout
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    /**
     * 로그인
     *
     * @param reqLogin 아이디, 패스워드 객체
     * @return
     */
    @POST("login")
    suspend fun postRequestLogin(
        @Body reqLogin: ReqLogin
    ): Response<ResLogin>

    /**
     * 로그아웃
     *
     * @param token 로그인 계정 토큰
     * @return
     */
    @POST("logout")
    suspend fun postRequestLogout(
        @Header("Authorization") token: String
    ): Response<ResLogout>
}