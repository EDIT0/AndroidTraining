package com.example.pagingdemo1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagingdemo1.R
import com.example.pagingdemo1.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
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
        binding.mainViewModel = mainViewModel

        binding.rvMovie.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(binding.root.context)
//            addOnScrollListener(onScrollListener)
        }

        movieAdapter.setOnItemClickListener { position, movieModelResult ->
            Log.i("MYTAG", "${position} ${movieModelResult}")
        }

        mainViewModel.result.observe(this as LifecycleOwner) {
            try {
                movieAdapter.submitData(this.lifecycle, it)
            } catch (e: Exception) {

            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.getPopularMovies()
                .collect {
                    movieAdapter.submitData(it)
                }
        }

        mainViewModel.etInput.observe(this as LifecycleOwner) {
            movieAdapter.submitData(lifecycle, PagingData.empty())
        }


//        lifecycleScope.launch(Dispatchers.IO) {
//            mainViewModel.getPopularMovies().collect {
//                Log.i("MYTAG", "${it}")
//                movieAdapter.submitData(lifecycle, it)
//            }
//        }

        binding.swipeRefresh.setOnRefreshListener {
            movieAdapter.refresh()

//            binding.swipeRefresh.isRefreshing = false
        }

        binding.btnRefresh.setOnClickListener {
            Log.i("MYTAG", "${mainViewModel.etInput.value}")
            movieAdapter.refresh()
        }

        movieAdapter.addLoadStateListener {
            Log.i("MYTAG", "prepend Loading ${it.source.prepend is LoadState.Loading}")
            Log.i("MYTAG", "append Loading ${it.source.append is LoadState.Loading}")
            Log.i("MYTAG", "refresh Loading ${it.source.refresh is LoadState.Loading}")

            // 리프래시 끝나면 없애기
            if(it.source.refresh !is LoadState.Loading) {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        // retry 말그대로 실패 후 재시도
        binding.apply {
            rvMovie.setHasFixedSize(true) // 사이즈 고정
            // header, footer 설정
            rvMovie.adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { movieAdapter.retry() },
                footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
        }

    }


}