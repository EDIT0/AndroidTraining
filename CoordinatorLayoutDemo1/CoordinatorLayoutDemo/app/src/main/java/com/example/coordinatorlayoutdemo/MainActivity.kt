package com.example.coordinatorlayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coordinatorlayoutdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : Adapter

    val itemList = listOf<Item>(
        Item("Mango"),
        Item("Apple"),
        Item("Banana"),
        Item("Guava"),
        Item("Lemon"),
        Item("Pear"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = Adapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.updateList(itemList)

    }
}