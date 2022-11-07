package com.example.mvcexample1.model

import android.app.Activity
import android.util.Log
import com.example.mvcexample1.BuildConfig
import com.example.mvcexample1.model.data.MovieModel
import com.example.mvcexample1.network.ApiService
import com.example.mvcexample1.util.ERROR
import com.example.mvcexample1.util.NO_DATA
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class Movie(
    private val apiService: ApiService
) {

    private val TAG = Movie::class.java.simpleName

    private var currentSearchQuery = ""

    private var movieList = ArrayList<MovieModel.MovieModelResult>()

    suspend fun searchMovies(name: String) : List<MovieModel.MovieModelResult> {
        try {
            val result = flow<MovieModel> {
                val response = apiService.getSearchMovies(
                    BuildConfig.API_KEY,
                    name,
                    "en_US",
                    1
                )

                if(response.isSuccessful) {
                    if(response.body()?.movieModelResults?.isEmpty() == true) {
                        throw Exception(NO_DATA)
                    } else {
                        response.body()?.let {
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
                }
                .onCompletion {
                    Log.i(TAG, "onCompletion()")
                }
                .catch {
                    Log.i(TAG, "catch : ${it.message}")
                }
                .collect {
                    Log.i(TAG, "${it}")
                    movieList = it.movieModelResults as ArrayList
                }

            return movieList

        } catch (e: Exception) {
            Log.i(TAG, "Request Catch : ${e.message}")
        }

        return emptyList<MovieModel.MovieModelResult>()
    }
}