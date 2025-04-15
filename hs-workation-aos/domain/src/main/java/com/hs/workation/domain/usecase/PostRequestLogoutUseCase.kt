package com.hs.workation.domain.usecase

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.ServiceResult
import com.hs.workation.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRequestLogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(token: String): Flow<RequestResult<ServiceResult>> {
        return authRepository.postRequestLogout(token)
    }
}