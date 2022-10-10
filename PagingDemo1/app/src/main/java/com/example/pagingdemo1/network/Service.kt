package com.example.pagingdemo1.network

import com.example.pagingdemo1.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
//    @GET("movie/popular")
//    suspend fun getPopularMovies(
//        @Query("api_key") api_key : String,
//        @Query("language") language : String,
//        @Query("page") page : Int
//    ) : Response<MovieModel>

    @GET("search/movie")
    suspend fun getPopularMovies(
        @Query("api_key") api_key : String,
        @Query("query") query: String,
        @Query("language") language : String,
        @Query("page") page : Int,
        @Query("include_adult") include_adult : Boolean = false
    ) : Response<MovieModel>
}