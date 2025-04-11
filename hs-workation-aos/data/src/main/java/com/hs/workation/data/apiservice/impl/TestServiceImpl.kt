package com.hs.workation.data.apiservice.impl

import com.hs.workation.data.apiservice.TestService
import com.hs.workation.core.model.test.res.ResTest1
import com.hs.workation.core.model.test.res.ResTest2
import retrofit2.Response
import javax.inject.Inject

class TestServiceImpl @Inject constructor(

): TestService {
    override suspend fun getTest1(): Response<ResTest1> {
        return Response.success(
            ResTest1(
                name = "TestName",
                number = 1,
                avgScore = 79.123
            )
        )
    }

    private var list = arrayListOf<ResTest2>(
        ResTest2(
            testPageInfo = ResTest2.TestPageInfo(
                totalPage = 8,
                page = 1
            ),
            subjectName = "Subject 1",
            subjectNumber = 1,
            questions = List(20) { index ->
                ResTest2.TestQuestion(
                    number = index + 1,
                    title = "Question Title 1 - ${index + 1}",
                    announce = "Announcement for Question 1 - ${index + 1}"
                )
            }
        ),
        ResTest2(
            testPageInfo = ResTest2.TestPageInfo(
                totalPage = 8,
                page = 2
            ),
            subjectName = "Subject 2",
            subjectNumber = 2,
            questions = List(20) { index ->
                ResTest2.TestQuestion(
                    number = index + 1,
                    title = "Question Title 2 - ${index + 1}",
                    announce = "Announcement for Question 2 - ${index + 1}"
                )
            }
        ),
        ResTest2(
            testPageInfo = ResTest2.TestPageInfo(
                totalPage = 8,
                page = 3
            ),
            subjectName = "Subject 3",
            subjectNumber = 3,
            questions = List(20) { index ->
                ResTest2.TestQuestion(
                    number = index + 1,
                    title = "Question Title 3 - ${index + 1}",
                    announce = "Announcement for Question 3 - ${index + 1}"
                )
            }
        ),
        ResTest2(
            testPageInfo = ResTest2.TestPageInfo(
                totalPage = 8,
                page = 4
            ),
            subjectName = "Subject 4",
            subjectNumber = 4,
            questions = List(20) { index ->
                ResTest2.TestQuestion(
                    number = index + 1,
                    title = "Question Title 4 - ${index + 1}",
                    announce = "Announcement for Question 4 - ${index + 1}"
                )
            }
        ),
        ResTest2(
            testPageInfo = ResTest2.TestPageInfo(
                totalPage = 8,
                page = 5
            ),
            subjectName = "Subject 5",
            subjectNumber = 5,
            questions = List(20) { index ->
                ResTest2.TestQuestion(
                    number = index + 1,
                    title = "Question Title 5 - ${index + 1}",
                    announce = "Announcement for Question 5 - ${index + 1}"
                )
            }
        ),
        ResTest2(
            testPageInfo = ResTest2.TestPageInfo(
                totalPage = 8,
                page = 6
            ),
            subjectName = "Subject 6",
            subjectNumber = 6,
            questions = List(20) { index ->
                ResTest2.TestQuestion(
                    number = index + 1,
                    title = "Question Title 6 - ${index + 1}",
                    announce = "Announcement for Question 6 - ${index + 1}"
                )
            }
        ),
        ResTest2(
            testPageInfo = ResTest2.TestPageInfo(
                totalPage = 8,
                page = 7
            ),
            subjectName = "Subject 7",
            subjectNumber = 7,
            questions = List(20) { index ->
                ResTest2.TestQuestion(
                    number = index + 1,
                    title = "Question Title 7 - ${index + 1}",
                    announce = "Announcement for Question 7 - ${index + 1}"
                )
            }
        ),
        ResTest2(
            testPageInfo = ResTest2.TestPageInfo(
                totalPage = 8,
                page = 8
            ),
            subjectName = "Subject 8",
            subjectNumber = 8,
            questions = List(20) { index ->
                ResTest2.TestQuestion(
                    number = index + 1,
                    title = "Question Title 8 - ${index + 1}",
                    announce = "Announcement for Question 8 - ${index + 1}"
                )
            }
        )
    )

    override suspend fun getTest2(page: Int): Response<ResTest2> {
        return Response.success(list[page-1])
    }

    override suspend fun deleteTest2Question(question: ResTest2.TestQuestion): Response<Boolean> {
        val mutableList = list.toMutableList()

        var updatedQuestions: MutableList<ResTest2.TestQuestion>? = null
        for(i in 0 until mutableList.size) {
            updatedQuestions = mutableList[i].questions?.toMutableList()
            for(j in 0 until updatedQuestions?.size!!) {
               if(updatedQuestions.get(j) == question) {
                   updatedQuestions.remove(question)

                   val updatedResTest2 = mutableList[i].copy(questions = updatedQuestions)
                   mutableList[i] = updatedResTest2
                   break
               }
            }
        }

        list = mutableList as ArrayList<ResTest2>

        return Response.success(true)
    }
}