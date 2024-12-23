package com.hs.workation.domain.usecase

import com.hs.workation.domain.model.base.RequestResult
import com.hs.workation.domain.model.res.ResTest2
import com.hs.workation.domain.repository.TestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteTest2QuestionUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend fun invoke(question: ResTest2.TestQuestion): Flow<RequestResult<Boolean>> {
        return testRepository.deleteTest2Question(question)
    }
}