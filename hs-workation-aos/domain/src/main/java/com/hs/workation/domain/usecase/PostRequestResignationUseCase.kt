package com.hs.workation.domain.usecase

import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.req.ReqResignation
import com.hs.workation.core.model.dto.res.ResResignation
import com.hs.workation.domain.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRequestResignationUseCase @Inject constructor(
    private val baseRepository: BaseRepository
) {

    suspend fun invoke(token: String, reqResignation: ReqResignation): Flow<RequestResult<ResResignation>> {
        return baseRepository.postRequestResignation(token, reqResignation)
    }
}