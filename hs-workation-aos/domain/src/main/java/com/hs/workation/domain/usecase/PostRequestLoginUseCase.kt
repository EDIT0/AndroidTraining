package com.hs.workation.domain.usecase

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.IdAndPassword
import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRequestLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(idAndPassword: IdAndPassword): Flow<RequestResult<Jwt>> {
        return authRepository.postRequestLogin(idAndPassword)
    }
}