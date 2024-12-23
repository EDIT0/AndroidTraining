package com.hs.workation.domain.repository

import androidx.paging.PagingData
import com.hs.workation.domain.model.base.RequestResult
import com.hs.workation.domain.model.req.ReqTest2
import com.hs.workation.domain.model.res.ResTest1
import com.hs.workation.domain.model.res.ResTest2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface TestRepository {
    suspend fun getTest1(): Flow<RequestResult<ResTest1>>
    suspend fun getTest2(reqTest2: ReqTest2, saveResTest2: MutableStateFlow<ResTest2>): Flow<PagingData<ResTest2.TestQuestion>>
    suspend fun deleteTest2Question(question: ResTest2.TestQuestion): Flow<RequestResult<Boolean>>
}