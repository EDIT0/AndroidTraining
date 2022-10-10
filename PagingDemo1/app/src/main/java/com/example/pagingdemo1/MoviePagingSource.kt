package com.example.pagingdemo1

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.network.Service
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    service: Service
) : PagingSource<Int, MovieModel.MovieModelResult>() {

    private var apiService: Service

    init {
        apiService = service
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
                "aaa",
                "en_US",
                position
            )

//            val response = simpleApi.getCustomPost()

            val post = response?.body()
            Log.i("MYTAG", "${post?.totalPages} / ${post?.page} / ${post?.movieModelResults}")

            /* 로드에 성공 시 LoadResult.Page 반환
            data : 전송되는 데이터
            prevKey : 이전 값 (위 스크롤 방향)
            nextKey : 다음 값 (아래 스크롤 방향)
             */
            LoadResult.Page(
                data = post!!.movieModelResults,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position == post.totalPages) null else position + 1
            )

            // 로드에 실패 시 LoadResult.Error 반환
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    // 데이터가 새로고침되거나 첫 로드 후 무효화되었을 때 키를 반환하여 load()로 전달
    override fun getRefreshKey(state: PagingState<Int, MovieModel.MovieModelResult>): Int? {
        TODO("Not yet implemented")
    }

}