package com.hs.workation.data.datasource.remote.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hs.workation.data.apiservice.TestService
import com.hs.workation.data.datasource.remote.TestRemoteDataSource
import com.hs.workation.data.datasource.remote.impl.pagingsource.Test2PagingSource
import com.hs.workation.domain.model.req.ReqTest2
import com.hs.workation.domain.model.res.ResTest1
import com.hs.workation.domain.model.res.ResTest2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import retrofit2.Response
import javax.inject.Inject

class TestRemoteDataSourceImpl @Inject constructor(
    private val testService: TestService
): TestRemoteDataSource {

    override suspend fun getTest1(): Response<ResTest1> {
        return testService.getTest1()
    }

    override suspend fun getTest2(
        reqTest2: ReqTest2,
        saveResTest2: MutableStateFlow<ResTest2>
    ): Flow<PagingData<ResTest2.TestQuestion>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false,
                initialLoadSize = 60,
                maxSize = 10000,
//                jumpThreshold =
            ),

            // 사용할 메소드 선언
            pagingSourceFactory = {
                Test2PagingSource(testService, reqTest2, saveResTest2)
            }
        ).flow.catch {
            throw Exception(it)
        }
    }

    override suspend fun deleteTest2Question(question: ResTest2.TestQuestion): Response<Boolean> {
        return testService.deleteTest2Question(question)
    }
}
