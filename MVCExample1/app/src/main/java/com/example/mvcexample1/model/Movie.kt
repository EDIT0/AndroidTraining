package com.example.mvcexample1.model

import android.app.Activity
import android.util.Log
import com.example.mvcexample1.BuildConfig
import com.example.mvcexample1.controller.MainActivity
import com.example.mvcexample1.model.data.MovieModel
import com.example.mvcexample1.network.ApiService
import com.example.mvcexample1.util.ERROR
import com.example.mvcexample1.util.NO_DATA
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class Movie(
    private val apiService: ApiService,
    private val movieCallback: MainActivity.movieCallback
) {

    private val TAG = Movie::class.java.simpleName

    private var currentSearchQuery = ""

    private var movieList = ArrayList<MovieModel.MovieModelResult>()

    private var isLoading = false
    private var totalPages = 0
    private var page = 0

    fun setSearchQuery(query: String) {
        currentSearchQuery = query
    }
    fun getLoadingState() : Boolean = isLoading
    fun getTotalPages() : Int = totalPages
    fun getCurrentPage() : Int = page
    fun setPage(page : Int) {
        this.page = page
    }

    suspend fun searchMovies() : List<MovieModel.MovieModelResult> {
        try {
            val result = flow<MovieModel> {
                val response = apiService.getSearchMovies(
                    BuildConfig.API_KEY,
                    currentSearchQuery,
                    "en_US",
                    page
                )

                if(response.isSuccessful) {
                    if(response.body()?.movieModelResults?.isEmpty() == true) {
                        throw Exception(NO_DATA)
                    } else {
                        response.body()?.let {
                            delay(1000L)
                            emit(it)
                        }
                    }
                } else {
                    throw Exception(ERROR)
                }
            }

            result
                .onStart {
                    Log.i(TAG, "onStart()")
                    isLoading = true
                    movieCallback.isLoading(isLoading)
                }
                .onCompletion {
                    Log.i(TAG, "onCompletion()")
                    isLoading = false
                    movieCallback.isLoading(isLoading)
                }
                .catch {
                    Log.i(TAG, "catch : ${it.message}")
                    throw Exception(it.message)
                }
                .collect {
                    Log.i(TAG, "${it}")
                    if(page == 1) {
                        Log.i(TAG, "Clear Movie List")
                        movieList.clear()
                    }
                    totalPages = it.totalPages
                    var tempArray = it.movieModelResults as ArrayList
                    movieList.addAll(tempArray)
                    page++
                }

            return movieList

        } catch (e: Exception) {
            Log.i(TAG, "Request Catch : ${e.message}")
        }

        return emptyList<MovieModel.MovieModelResult>()
    }
}