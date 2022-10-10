package com.example.pagingdemo1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagingdemo1.R
import com.example.pagingdemo1.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    var movieAdapter: MovieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        binding.rvMovie.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(applicationContext)
//            addOnScrollListener(onScrollListener)
        }

        movieAdapter.setOnItemClickListener { position, movieModelResult ->
            Log.i("MYTAG", "${position} ${movieModelResult}")
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                mainViewModel.getPopularMovies().collect {
                    Log.i("MYTAG", "${it}")
                    movieAdapter.submitData(it)
                }
            } catch (e:Exception) {

            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            movieAdapter.submitData(lifecycle, PagingData.empty())

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    mainViewModel.getPopularMovies().collect {
                        Log.i("MYTAG", "${it}")
                        movieAdapter.submitData(it)
                    }
                } catch (e:Exception) {

                }
            }

            binding.swipeRefresh.isRefreshing = false
        }


    }


}