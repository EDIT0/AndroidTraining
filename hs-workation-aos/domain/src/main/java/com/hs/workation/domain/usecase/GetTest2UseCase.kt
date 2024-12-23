package com.hs.workation.domain.usecase

import androidx.paging.PagingData
import com.hs.workation.domain.model.req.ReqTest2
import com.hs.workation.domain.model.res.ResTest2
import com.hs.workation.domain.repository.TestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetTest2UseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend fun invoke(reqTest2: ReqTest2, saveResTest2: MutableStateFlow<ResTest2>): Flow<PagingData<ResTest2.TestQuestion>> {
        return testRepository.getTest2(reqTest2, saveResTest2)
    }
}