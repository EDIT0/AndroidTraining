package com.example.pagingdemo1.repo.remote

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.MoviePagingSource
import com.example.pagingdemo1.network.Service
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val service: Service
) : RemoteDataSource {
    //    override suspend fun getPopularMovies(language: String, page: Int): Response<MovieModel> {
//        return service.getPopularMovies(BuildConfig.API_KEY, language, page)
//    }
    override fun getPopularMovies(query: String, language: String, page: Int): Flow<PagingData<MovieModel.MovieModelResult>> {
        return Pager(
            /**
             * pageSize:
             *  각 페이지에서 로드해야 하는 항목의 수
             *
             * prefetchDistance:
             *  PagedList 의 최상단/최하단에 도달하기 얼마 전에 추가 로딩을 수행하지에 대한 설정
             *  최소 pageSize + (2 * prefetchDistance) 보다 높게 설정
             *
             * enablePlaceholders:
             *  true - 아직 로드되지 않은 콘텐츠의 placeholder로 null 반환
             *  false - placeholder 사용 중지
             *
             * maxSize:
             *  PagingData 객체에서 로드하고 있는 항목의 최대 갯수
             *
             * jumpThreshold:
             *  잘 모르겠음
             * */
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false,
                initialLoadSize = 60,
                maxSize = 300,
//                jumpThreshold =
            ),

            // 사용할 메소드 선언
            pagingSourceFactory = { MoviePagingSource(service, query)}
        ).flow

    }
}