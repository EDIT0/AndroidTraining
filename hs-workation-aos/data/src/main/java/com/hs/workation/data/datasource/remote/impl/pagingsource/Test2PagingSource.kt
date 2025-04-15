package com.hs.workation.data.datasource.remote.impl.pagingsource

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hs.workation.data.apiservice.TestService
import com.hs.workation.core.model.test.req.ReqTest2
import com.hs.workation.core.model.test.res.ResTest2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.IOException
import java.util.Random

private const val STARTING_PAGE_INDEX = 1

class Test2PagingSource(
    private val testService: TestService,
    private val reqTest2: ReqTest2,
    private val _saveResTest2: MutableStateFlow<ResTest2>
) : PagingSource<Int, ResTest2.TestQuestion>() {

    // 데이터 로드
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResTest2.TestQuestion> {
        // LoadParams : 로드할 키와 항목 수 , LoadResult : 로드 작업의 결과
        return try {
            // 키 값이 없을 경우 기본값을 사용함
            val position = params.key ?: STARTING_PAGE_INDEX

            // 데이터를 제공하는 인스턴스의 메소드 사용
            val response = testService.getTest2(position)

            var post: ResTest2? = null
            post = response.body()

            delay(1000L)

            // 에러 발생 시키기
            val randomNumber = Random().nextInt(10) + 1
            if(randomNumber > 8) {
                throw IOException("IOException Error 만듬")
            }

            post?.let {
                _saveResTest2.value = it // ResTest2 전체를 saveResTest2에 저장

                LoadResult.Page(
                    data = post?.questions!!,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (position == post.testPageInfo?.totalPage) null else position + 1,
                    itemsBefore = 0,
                    itemsAfter = 0
                )
            }?: throw Exception("PagingSource Error")

        // 로드에 실패 시 LoadResult.Error 반환
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    // 데이터가 새로고침되거나 첫 로드 후 무효화되었을 때 키를 반환하여 load()로 전달
    override fun getRefreshKey(state: PagingState<Int, ResTest2.TestQuestion>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}