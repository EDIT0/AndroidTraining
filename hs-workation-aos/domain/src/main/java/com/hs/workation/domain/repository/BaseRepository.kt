package com.hs.workation.domain.repository

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.req.ReqResignation
import com.hs.workation.core.model.dto.req.ReqSignUp
import com.hs.workation.core.model.dto.res.ResResignation
import com.hs.workation.core.model.dto.res.ResSignUp
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    suspend fun postRequestSignUp(reqSignUp: ReqSignUp): Flow<RequestResult<ResSignUp>>
    suspend fun postRequestResignation(token: String, reqResignation: ReqResignation): Flow<RequestResult<ResResignation>>
}