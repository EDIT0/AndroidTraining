package com.hs.workation.data.repository

import android.util.Log
import androidx.paging.PagingData
import com.hs.workation.data.datasource.remote.TestRemoteDataSource
import com.hs.workation.domain.model.base.RequestResult
import com.hs.workation.domain.model.req.ReqTest2
import com.hs.workation.domain.model.res.ResTest1
import com.hs.workation.domain.model.res.ResTest2
import com.hs.workation.domain.repository.TestRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testRemoteDataSource: TestRemoteDataSource
): TestRepository {

    override suspend fun getTest1(): Flow<RequestResult<ResTest1>> {
        return flow<RequestResult<ResTest1>> {
            val response = testRemoteDataSource.getTest1()

            try {
                if(response.isSuccessful) {
                    val data = response.body()
                    if(data != null) {
                        emit(RequestResult.Success(data))
                    } else {
                        emit(RequestResult.DataEmpty())
                    }
                } else {
                    emit(RequestResult.Error(code = "Test1ErrorCode", message = "Test1ErrorMessage"))
                }
            } catch (e: Exception) {
                throw Exception(e)
            }
        }.catch {
            throw Exception(it)
        }
    }

    override suspend fun getTest2(reqTest2: ReqTest2, saveResTest2: MutableStateFlow<ResTest2>): Flow<PagingData<ResTest2.TestQuestion>> {
        return testRemoteDataSource.getTest2(reqTest2 = reqTest2, saveResTest2 = saveResTest2)
    }

    override suspend fun deleteTest2Question(question: ResTest2.TestQuestion): Flow<RequestResult<Boolean>> {
        return flow<RequestResult<Boolean>> {
            val response = testRemoteDataSource.deleteTest2Question(question)

            try {
                if(response.isSuccessful) {
                    val data = response.body()
                    if(data != null) {
                        emit(RequestResult.Success(data))
                    } else {
                        emit(RequestResult.DataEmpty())
                    }
                } else {
                    emit(RequestResult.Error(code = "DeleteTest2QuestionErrorCode", message = "DeleteTest2QuestionErrorMessage"))
                }
            } catch (e: Exception) {
                throw Exception(e)
            }
        }.catch {
            throw Exception(it)
        }
    }
}