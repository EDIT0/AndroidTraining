package com.hs.workation.domain.usecase

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.res.ResLogout
import com.hs.workation.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRequestLogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(token: String): Flow<RequestResult<ResLogout>> {
        return authRepository.postRequestLogout(token)
    }
}