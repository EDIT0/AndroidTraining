package com.edit.skeletonloading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.edit.skeletonloading.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var adapter = Adapter()
    private var list = ArrayList<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvData.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvData.adapter = adapter

        list.add(
            Model(0, "철수", "King", "https://picsum.photos/200")
        )
        list.add(
            Model(1, "영희", "어..", "https://picsum.photos/200/300")
        )
        list.add(
            Model(2, "맹구", "콧물", "https://picsum.photos/200")
        )
        list.add(
            Model(3, "짱아", "짱", "https://picsum.photos/200/300")
        )
        list.add(
            Model(4, "영수", "영수나라", "https://picsum.photos/200")
        )

        CoroutineScope(Dispatchers.Main).launch {
            showSampleData(isLoading = true)
            delay(5000L)
            adapter.submitList(list.toMutableList())
            showSampleData(isLoading = false)
        }

    }

    private fun showSampleData(isLoading: Boolean) {
        if (isLoading) {
            binding.sflSample.startShimmer()
            binding.sflSample.visibility = View.VISIBLE
            binding.rvData.visibility = View.GONE
        } else {
            binding.sflSample.stopShimmer()
            binding.sflSample.visibility = View.GONE
            binding.rvData.visibility = View.VISIBLE
        }
    }
}