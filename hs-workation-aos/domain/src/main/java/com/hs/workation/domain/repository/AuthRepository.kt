package com.hs.workation.domain.repository

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.core.model.test.req.ReqLogin
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun postRequestLogin(reqLogin: ReqLogin): Flow<RequestResult<Jwt>>
}