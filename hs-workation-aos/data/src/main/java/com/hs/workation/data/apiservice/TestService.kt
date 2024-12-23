package com.hs.workation.data.apiservice

import com.hs.workation.domain.model.res.ResTest1
import com.hs.workation.domain.model.res.ResTest2
import retrofit2.Response

interface TestService {

    suspend fun getTest1() : Response<ResTest1>
    suspend fun getTest2(page: Int) : Response<ResTest2>
    suspend fun deleteTest2Question(question: ResTest2.TestQuestion) : Response<Boolean>
}