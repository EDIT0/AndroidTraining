package com.hs.workation.domain.usecase

import com.hs.workation.domain.model.base.RequestResult
import com.hs.workation.domain.model.res.ResTest1
import com.hs.workation.domain.repository.TestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTest1UseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend fun invoke(): Flow<RequestResult<ResTest1>> {
        return testRepository.getTest1()
    }
}