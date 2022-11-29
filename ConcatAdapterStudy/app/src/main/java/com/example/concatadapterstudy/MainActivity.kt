package com.example.concatadapterstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.concatadapterstudy.adapter.*
import com.example.concatadapterstudy.databinding.ActivityMainBinding
import com.example.concatadapterstudy.model.FooterModel
import com.example.concatadapterstudy.model.HeaderModel
import com.example.concatadapterstudy.model.HorizontalModel
import com.example.concatadapterstudy.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding : ActivityMainBinding

    private lateinit var tmdbAPIService: TmdbAPIService

    val BASE_URL = "https://api.themoviedb.org/3/"
    val API_KEY = "6db59bbd7b6f0437a11a5d733f5889fb"

    private lateinit var concatAdapter: ConcatAdapter

    private lateinit var headerAdapter: HeaderAdapter
    private lateinit var bodyAdapter: BodyAdapter
    private lateinit var footerAdapter: FooterAdapter
    private lateinit var horizontalAdapter: HorizontalAdapter

    private var language = "en-US"
    private var isLoading = false

    private var page = 1
    private var totalPages = 0

    private val bodyList : MutableLiveData<Response<MovieModel>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 통신 연결
        settingRetrofit()

        // 호리젠탈, 헤더, 바디, 푸터 어댑터 생성
        horizontalAdapter = HorizontalAdapter()
        val horizontalList = listOf<HorizontalModel>(
            HorizontalModel("1"),
            HorizontalModel("2"),
            HorizontalModel("3"),
            HorizontalModel("4"),
            HorizontalModel("5"),
            HorizontalModel("6"),
            HorizontalModel("7"),
            HorizontalModel("8"),

        )
        horizontalAdapter.submitList(horizontalList.toMutableList())

        headerAdapter = HeaderAdapter().apply {
            setHasStableIds(true)
        }
        val headerList = listOf<HeaderModel>(
            HeaderModel("첫 번째 박스"),
            HeaderModel("두 번째 박스"),
            HeaderModel("마지막 박스")
        )
        headerAdapter.submitList(headerList.toMutableList())
        bodyAdapter = BodyAdapter().apply {
            setHasStableIds(true)
        }
        footerAdapter = FooterAdapter().apply {
            setHasStableIds(true)
        }

        // ConcatAdapter 설정
        initRecyclerView()

        // Data ObserveListener
        observeListener()

        // 맨 하단에 로딩바 넣기
        if(footerAdapter.currentList.size == 0) {
            footerAdapter.submitList(listOf<FooterModel>(FooterModel("로딩 중...")).toMutableList())
        }
        getBodyData(page)
    }

    private fun settingRetrofit() {
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

        tmdbAPIService = retrofit.create(TmdbAPIService::class.java)
    }

    private fun initRecyclerView() {
        val config = ConcatAdapter.Config.Builder()
//            .setStableIdMode(ConcatAdapter.Config.StableIdMode.NO_STABLE_IDS)
//            .setStableIdMode(ConcatAdapter.Config.StableIdMode.ISOLATED_STABLE_IDS)
//            .setIsolateViewTypes(true)
            .setIsolateViewTypes(false)
            .build()

        concatAdapter = ConcatAdapter(config, headerAdapter, BaseHorizontalConcatAdapter(this, horizontalAdapter), bodyAdapter, footerAdapter)

        binding.rvConcat.apply {
            adapter = concatAdapter
//            layoutManager = LinearLayoutManager(this@MainActivity)
            addOnScrollListener(this@MainActivity.onScrollListener)
        }

        // 지정 SpanCount에 따라 칸 나뉨
        binding.rvConcat.layoutManager = GridLayoutManager(this, 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when(concatAdapter.getItemViewType(position)) {
                        // 몇 칸을 차지 할 것 인지 지정
                        R.layout.horizontal_list_item, R.layout.body_list_item, R.layout.footer_list_item -> 3
                        R.layout.header_list_item -> 2
                        else -> 1
                    }
                }
            }
        }

        headerAdapter.setOnItemClickListener { adapterPosition, it ->
            Toast.makeText(this@MainActivity, "${adapterPosition}번째 데이터\n${it.headerText}",Toast.LENGTH_SHORT).show()
        }

        horizontalAdapter.setOnItemClickListener { adapterPosition, it ->
            Toast.makeText(this@MainActivity, "${adapterPosition}번째 데이터\n${it.horizontalText}",Toast.LENGTH_SHORT).show()
        }

        bodyAdapter.setOnItemClickListener { adapterPosition, it ->
            Toast.makeText(this@MainActivity, "${adapterPosition}번째 데이터\n${it.title}",Toast.LENGTH_SHORT).show()
        }

        footerAdapter.setOnItemClickListener { adapterPosition, it ->
            Toast.makeText(this@MainActivity, "${adapterPosition}번째 데이터\n${it.state}",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar() {
        isLoading = true
    }

    private fun hideProgressBar() {
        isLoading = false
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
            val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

            // 스크롤이 끝에 도달했는지 확인
            val b = itemTotalCount - 5
            Log.i(TAG, "lastVisibleItemPosition: ${lastVisibleItemPosition} b: ${b}")
            if (lastVisibleItemPosition > b) {
                Log.i(TAG, "a==b!!")
                if(isLoading) {

                } else {
                    if(totalPages != page && bodyAdapter.currentList.size > 0) {
                        page++
                        showProgressBar()
                        getBodyData(page)
                    } else {
                        // 마지막 불러올 데이터 없음
                        footerAdapter.submitList(emptyList())
                    }
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }

    private fun getHeaderData() {
        val currentList : List<HeaderModel> = headerAdapter.currentList
        val newList : List<HeaderModel> = listOf(HeaderModel("추가"))
        val updateList = currentList + newList
        headerAdapter.submitList(updateList.toMutableList())
    }

    private fun getBodyData(page: Int) {
        Log.i(TAG, "데이터 호출!")
        lifecycleScope.launch(Dispatchers.IO) {
            delay(2000L)
            bodyList.postValue(tmdbAPIService.getPopularMovies(API_KEY, language, page))
//            bodyList.postValue(tmdbAPIService.getSearchMovies(API_KEY, "aaa", language, page))
        }
    }

    private fun observeListener() {
        bodyList.observe(this@MainActivity as LifecycleOwner, {
            Log.i(TAG, "전체 페이지: ${it.body()?.totalPages} / 받아온 데이터 : ${it.body()}")
            totalPages = it.body()?.totalPages?:0

            if(it.isSuccessful && it.body()?.movieModelResults != null) {
                val currentList : List<MovieModel.MovieModelResult> = bodyAdapter.currentList
                val newList : List<MovieModel.MovieModelResult> = it.body()?.movieModelResults!!
                val updateList = currentList + newList
                bodyAdapter.submitList(updateList.toMutableList()) {
                    footerAdapter.submitList(emptyList()) {
                        // 맨 하단에 로딩바 넣기
                        if(footerAdapter.currentList.size == 0) {
                            footerAdapter.submitList(listOf<FooterModel>(FooterModel("로딩 중...")).toMutableList())
                        }
                    }
                }
            }

            hideProgressBar()
        })
    }
}