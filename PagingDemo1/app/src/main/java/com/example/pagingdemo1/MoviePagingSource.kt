package com.example.pagingdemo1

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.network.Service
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import java.util.*

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    service: Service,
    query: String
) : PagingSource<Int, MovieModel.MovieModelResult>() {

    private var apiService: Service
    private lateinit var query: String

    init {
        apiService = service
        this.query = query
    }

    // 데이터 로드
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel.MovieModelResult> {
        // LoadParams : 로드할 키와 항목 수 , LoadResult : 로드 작업의 결과
        return try {
            // 키 값이 없을 경우 기본값을 사용함
            val position = params.key ?: STARTING_PAGE_INDEX
//            Log.i("MYTAG", "${position}")

            // 데이터를 제공하는 인스턴스의 메소드 사용
            val response = apiService.getPopularMovies(
                BuildConfig.API_KEY,
                query,
                "en_US",
                position
            )

//            val response = simpleApi.getCustomPost()

            val post = response?.body()
            Log.i("MYTAG", "${post?.totalPages} / ${post?.page} / ${post?.movieModelResults}")
            if(post == null || post.totalPages == 0) {
                throw IOException()
            }

            delay(1000L)

            if(post.page % 3 == 0) {
                val randomNumber = Random().nextInt(10) + 1
                if(randomNumber > 5) {
                    throw IOException()
                }
            }

            /* 로드에 성공 시 LoadResult.Page 반환
            data : 전송되는 데이터
            prevKey : 이전 값 (위 스크롤 방향)
            nextKey : 다음 값 (아래 스크롤 방향)
            itemsBefore, itemsAfter : 리스트 앞, 뒤에 표시할 placeholder 수
             */
            LoadResult.Page(
                data = post!!.movieModelResults,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position == post.totalPages) null else position + 1,
                itemsBefore = 0,
                itemsAfter = 0
            )

            // 로드에 실패 시 LoadResult.Error 반환
        } catch (e: IOException) {
            Log.i("MYTAG", "IOException: ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.i("MYTAG", "HttpException: ${e.message}")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.i("MYTAG", "Exception: ${e.message}")
            LoadResult.Error(e)
        }
    }

    // 데이터가 새로고침되거나 첫 로드 후 무효화되었을 때 키를 반환하여 load()로 전달
    override fun getRefreshKey(state: PagingState<Int, MovieModel.MovieModelResult>): Int? {
        return state.anchorPosition?.let {
            Log.i("MYTAG", "getRefreshKey() ${state.anchorPosition} ${state.closestPageToPosition(it)?.prevKey} ${state.closestPageToPosition(it)?.nextKey}")
            Log.i("MYTAG", "getRefreshKey() ${state.closestPageToPosition(it)?.prevKey?.plus(1)?: state.closestPageToPosition(it)?.nextKey?.minus(1)}")
//            state.closestPageToPosition(it)?.prevKey?.plus(1)?: state.closestPageToPosition(it)?.nextKey?.minus(1)
            STARTING_PAGE_INDEX
        }
    }

}