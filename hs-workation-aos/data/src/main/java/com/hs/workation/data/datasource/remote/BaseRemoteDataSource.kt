package com.hs.workation.data.datasource.remote

import com.hs.workation.core.model.dto.req.ReqResignation
import com.hs.workation.core.model.dto.req.ReqSignUp
import com.hs.workation.core.model.dto.res.ResResignation
import com.hs.workation.core.model.dto.res.ResSignUp
import retrofit2.Response

interface BaseRemoteDataSource {
    suspend fun postRequestSignUp(reqSignUp: ReqSignUp): Response<ResSignUp>
    suspend fun postRequestResignation(token: String, reqResignation: ReqResignation): Response<ResResignation>
}