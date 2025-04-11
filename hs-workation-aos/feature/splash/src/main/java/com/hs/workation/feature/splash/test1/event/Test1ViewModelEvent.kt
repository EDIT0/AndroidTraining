package com.hs.workation.feature.splash.test1.event

import com.hs.workation.core.model.test.req.ReqTest2
import com.hs.workation.core.model.test.res.ResTest2

sealed interface Test1ViewModelEvent {
    class GetTest1(): Test1ViewModelEvent
    class GetTest2(val reqTest2: ReqTest2): Test1ViewModelEvent
    class DeleteTest2Question(val question: ResTest2.TestQuestion): Test1ViewModelEvent
}