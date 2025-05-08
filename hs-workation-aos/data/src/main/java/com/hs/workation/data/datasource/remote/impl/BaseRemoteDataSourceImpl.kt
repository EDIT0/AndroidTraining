package com.hs.workation.data.datasource.remote.impl

import com.hs.workation.core.model.dto.req.ReqResignation
import com.hs.workation.core.model.dto.req.ReqSignUp
import com.hs.workation.core.model.dto.res.ResResignation
import com.hs.workation.core.model.dto.res.ResSignUp
import com.hs.workation.data.apiservice.BaseApiService
import com.hs.workation.data.datasource.remote.BaseRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class BaseRemoteDataSourceImpl @Inject constructor(
    private val baseApiService: BaseApiService
): BaseRemoteDataSource {

    override suspend fun postRequestSignUp(reqSignUp: ReqSignUp): Response<ResSignUp> {
        return baseApiService.postRequestSignUp(reqSignUp)
    }

    override suspend fun postRequestResignation(token: String, reqResignation: ReqResignation): Response<ResResignation> {
        return baseApiService.postRequestResignation(token, reqResignation)
    }

}