package com.hs.workation.domain.usecase

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.req.ReqSignUp
import com.hs.workation.core.model.dto.res.ResSignUp
import com.hs.workation.domain.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRequestSignUpUseCase @Inject constructor(
    private val baseRepository: BaseRepository
) {

    suspend fun invoke(reqSignUp: ReqSignUp): Flow<RequestResult<ResSignUp>> {
        return baseRepository.postRequestSignUp(reqSignUp)
    }
}