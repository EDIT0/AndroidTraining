package com.example.coroutinestudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestudy.api.TmdbAPIService
import com.example.coroutinestudy.databinding.ActivityAsynchronousAndSequentialExecutionBinding
import com.example.coroutinestudy.model.MovieModel
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor


class AsynchronousAndSequentialExecutionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAsynchronousAndSequentialExecutionBinding

    val BASE_URL = "https://api.themoviedb.org/3/"
    val API_KEY = ""

    val popularMoviesPage1: MutableLiveData<Response<MovieModel>> = MutableLiveData()
    val popularMoviesPage2: MutableLiveData<Response<MovieModel>> = MutableLiveData()
    val popularMoviesPage3: MutableLiveData<Response<MovieModel>> = MutableLiveData()
    val popularMoviesPage4: MutableLiveData<Response<MovieModel>> = MutableLiveData()
    val popularMoviesPage5: MutableLiveData<Response<MovieModel>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsynchronousAndSequentialExecutionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()

        val retrofitApi = retrofit.create(TmdbAPIService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val a = retrofitApi.getPopularMovies(API_KEY, "ko-KR", 1)
            Log.i("MYTAG", "page 1 execute!! ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                popularMoviesPage1.setValue(a)
            }
            val b = retrofitApi.getPopularMovies(API_KEY, "ko-KR", a.body()?.page!! + 1)
            Log.i("MYTAG", "page 2 execute!! ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                popularMoviesPage2.setValue(b)
            }
            val c = retrofitApi.getPopularMovies(API_KEY, "ko-KR", b.body()?.page!! + 1)
            Log.i("MYTAG", "page 3 execute!! ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                popularMoviesPage3.setValue(c)
            }
            val d = retrofitApi.getPopularMovies(API_KEY, "ko-KR", c.body()?.page!! + 1)
            Log.i("MYTAG", "page 4 execute!! ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                popularMoviesPage4.setValue(d)
            }
            val e = retrofitApi.getPopularMovies(API_KEY, "ko-KR", d.body()?.page!! + 1)
            Log.i("MYTAG", "page 5 execute!! ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                popularMoviesPage5.setValue(e)
            }

        }

        popularMoviesPage1.observe(this as LifecycleOwner, {
            Log.i("MYTAG", "${Thread.currentThread().name} / page is ${it.body()?.page} / ${it.body().toString()}")
        })

        popularMoviesPage2.observe(this as LifecycleOwner, {
            Log.i("MYTAG", "${Thread.currentThread().name} / page is ${it.body()?.page} / ${it.body().toString()}")
        })

        popularMoviesPage3.observe(this as LifecycleOwner, {
            Log.i("MYTAG", "${Thread.currentThread().name} / page is ${it.body()?.page} / ${it.body().toString()}")
        })

        popularMoviesPage4.observe(this as LifecycleOwner, {
            Log.i("MYTAG", "${Thread.currentThread().name} / page is ${it.body()?.page} / ${it.body().toString()}")
        })

        popularMoviesPage5.observe(this as LifecycleOwner, {
            Log.i("MYTAG", "${Thread.currentThread().name} / page is ${it.body()?.page} / ${it.body().toString()}")
        })

    }
}