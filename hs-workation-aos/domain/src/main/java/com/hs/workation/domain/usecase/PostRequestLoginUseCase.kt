package com.hs.workation.domain.usecase

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.req.ReqLogin
import com.hs.workation.core.model.dto.res.ResLogin
import com.hs.workation.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRequestLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(reqLogin: ReqLogin): Flow<RequestResult<ResLogin>> {
        return authRepository.postRequestLogin(reqLogin)
    }
}