package com.hs.workation.data.datasource.remote

import com.hs.workation.core.model.dto.IdAndPassword
import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.core.model.dto.ServiceResult
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun postRequestLogin(idAndPassword: IdAndPassword): Response<Jwt>
    suspend fun postRequestLogout(token: String): Response<ServiceResult>
}