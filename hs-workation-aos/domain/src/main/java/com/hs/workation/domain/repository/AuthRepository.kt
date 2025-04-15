package com.hs.workation.domain.repository

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.IdAndPassword
import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.core.model.dto.ServiceResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun postRequestLogin(idAndPassword: IdAndPassword): Flow<RequestResult<Jwt>>
    suspend fun postRequestLogout(token: String): Flow<RequestResult<ServiceResult>>
}