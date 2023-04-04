package com.edit.multiplervviewholderexample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edit.multiplervviewholderexample1.databinding.ActivityMainBinding
import com.edit.multiplervviewholderexample1.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dataAdapter = DataAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val dataList = ArrayList<Model>()
    private val dataRepository = ArrayList<Model>()
    private var start = 0
    private var end = 10
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linearLayoutManager = LinearLayoutManager(binding.root.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvData.layoutManager = linearLayoutManager
        binding.rvData.adapter = dataAdapter
        binding.rvData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                // 스크롤이 끝에 도달했는지 확인
                val number = itemTotalCount
                Log.i("MYTAG", "${lastVisibleItemPosition} / ${number} / ${isLoading}")
                if (lastVisibleItemPosition == number) {
                    if (!isLoading) {
                        if (isLoading) {

                        } else {
                            Log.i("MYTAG", "${dataRepository.size} / ${dataList.size}")
                            if (dataRepository.size != dataList.size && (dataList.size ?: 0) > 0) {
                                Log.i("MYTAG", "${binding.rvData.canScrollVertically(-1)}")
//                                if (binding.rvData.canScrollVertically(-1)) { // 최상단 Refesh 할 경우 호출 되는 것을 막기 위해 넣음
                                    getData(start, end)
//                                }
                            } else {
                                dataAdapter.submitList(emptyList())
                            }
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        setData()
        getData(0, 10)
    }

    private fun setData() {
        dataRepository.apply {
            add(FirstModel(0, "1", ViewHolderType.FIRST))
            add(ThirdModel(1, "3", ViewHolderType.THIRD))
            add(FirstModel(2, "1", ViewHolderType.FIRST))
            add(SecondModel(3, "2", ViewHolderType.SECOND))
            add(ThirdModel(4, "3", ViewHolderType.THIRD))
            add(SecondModel(5, "2", ViewHolderType.SECOND))
            add(FirstModel(6, "1", ViewHolderType.FIRST))
            add(FirstModel(7, "1", ViewHolderType.FIRST))
            add(FirstModel(8, "1", ViewHolderType.FIRST))
            add(ThirdModel(9, "3", ViewHolderType.THIRD))
            add(FirstModel(10, "1", ViewHolderType.FIRST))
            add(FirstModel(11, "1", ViewHolderType.FIRST))
            add(SecondModel(12, "2", ViewHolderType.SECOND))
            add(FirstModel(13, "1", ViewHolderType.FIRST))
            add(FirstModel(14, "1", ViewHolderType.FIRST))
            add(FirstModel(15, "1", ViewHolderType.FIRST))
            add(SecondModel(16, "2", ViewHolderType.SECOND))
            add(SecondModel(17, "2", ViewHolderType.SECOND))
            add(FirstModel(18, "1", ViewHolderType.FIRST))
            add(FirstModel(19, "1", ViewHolderType.FIRST))
            add(SecondModel(20, "2", ViewHolderType.SECOND))
            add(FirstModel(21, "1", ViewHolderType.FIRST))
            add(SecondModel(22, "2", ViewHolderType.SECOND))
            add(FirstModel(23, "1", ViewHolderType.FIRST))
            add(ThirdModel(24, "3", ViewHolderType.THIRD))
            add(FirstModel(25, "1", ViewHolderType.FIRST))
            add(SecondModel(26, "2", ViewHolderType.SECOND))
            add(SecondModel(27, "2", ViewHolderType.SECOND))
            add(ThirdModel(28, "3", ViewHolderType.THIRD))
            add(FirstModel(29, "1", ViewHolderType.FIRST))
            add(SecondModel(30, "2", ViewHolderType.SECOND))
            add(ThirdModel(31, "3", ViewHolderType.THIRD))
            add(FirstModel(32, "1", ViewHolderType.FIRST))
        }
    }

    private fun getData(startNum: Int, endNum: Int) {
            if(!isLoading) {
                isLoading = true
                CoroutineScope(Dispatchers.Main).launch {
                    delay(3000L)
                    for (i in startNum until endNum) {
                        if (dataRepository.size <= i) {
                            dataList.remove(LoadingModel(-1, ViewHolderType.LOADING))
                            dataAdapter.submitList(dataList.toMutableList())
                            return@launch
                        }
                        dataList.add(dataRepository[i])
                    }
                    dataList.remove(LoadingModel(-1, ViewHolderType.LOADING))
                    dataList.add(LoadingModel(-1, ViewHolderType.LOADING))
                    dataAdapter.submitList(dataList.toMutableList())
                    start = endNum
                    end = endNum + 10
                    isLoading = false
                }
            }
    }
}