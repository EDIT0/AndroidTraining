package com.hs.workation.feature.splash.test1.state

import androidx.paging.PagingData
import com.hs.workation.core.model.test.res.ResTest1
import com.hs.workation.core.model.test.res.ResTest2

data class Test1UiState(
    val resTest1: ResTest1? = null,
    val resTest2: ResTest2? = null,
    val questions: PagingData<ResTest2.TestQuestion>? = null
)