package com.example.coroutinestudy.api

import com.example.coroutinestudy.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbAPIService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key : String,
        @Query("language") language : String,
        @Query("page") page : Int
    ) : Response<MovieModel>

}