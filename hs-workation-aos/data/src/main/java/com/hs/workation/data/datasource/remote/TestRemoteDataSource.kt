package com.hs.workation.data.datasource.remote

import androidx.paging.PagingData
import com.hs.workation.domain.model.req.ReqTest2
import com.hs.workation.domain.model.res.ResTest1
import com.hs.workation.domain.model.res.ResTest2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response

interface TestRemoteDataSource {
    suspend fun getTest1(): Response<ResTest1>
    suspend fun getTest2(reqTest2: ReqTest2, saveResTest2: MutableStateFlow<ResTest2>): Flow<PagingData<ResTest2.TestQuestion>>
    suspend fun deleteTest2Question(question: ResTest2.TestQuestion): Response<Boolean>
}