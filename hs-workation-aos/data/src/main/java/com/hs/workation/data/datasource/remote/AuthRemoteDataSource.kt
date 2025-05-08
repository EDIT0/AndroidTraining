package com.hs.workation.data.datasource.remote

import com.hs.workation.core.model.dto.req.ReqLogin
import com.hs.workation.core.model.dto.res.ResLogin
import com.hs.workation.core.model.dto.res.ResLogout
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun postRequestLogin(reqLogin: ReqLogin): Response<ResLogin>
    suspend fun postRequestLogout(token: String): Response<ResLogout>
}