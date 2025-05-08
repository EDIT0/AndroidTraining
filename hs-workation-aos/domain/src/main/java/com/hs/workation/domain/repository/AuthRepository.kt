package com.hs.workation.domain.repository

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.req.ReqLogin
import com.hs.workation.core.model.dto.res.ResLogin
import com.hs.workation.core.model.dto.res.ResLogout
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun postRequestLogin(reqLogin: ReqLogin): Flow<RequestResult<ResLogin>>
    suspend fun postRequestLogout(token: String): Flow<RequestResult<ResLogout>>
}