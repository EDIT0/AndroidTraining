package com.hs.workation.data.apiservice

import com.hs.workation.core.model.dto.req.ReqResignation
import com.hs.workation.core.model.dto.req.ReqSignUp
import com.hs.workation.core.model.dto.res.ResResignation
import com.hs.workation.core.model.dto.res.ResSignUp
import com.hs.workation.data.BuildConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BaseApiService {

    /**
     * 회원가입
     *
     * @param reqSignUp
     * @return
     */
    @POST("${BuildConfig.BASE_PATH}/signup")
    suspend fun postRequestSignUp(
        @Body reqSignUp: ReqSignUp
    ): Response<ResSignUp>

    /**
     * 회원 탈퇴 요청
     *
     * @param token
     * @param reqResignation
     * @return
     */
    @POST("${BuildConfig.BASE_PATH}/resignations")
    suspend fun postRequestResignation(
        @Header("Authorization") token: String,
        @Body reqResignation: ReqResignation
    ): Response<ResResignation>
}