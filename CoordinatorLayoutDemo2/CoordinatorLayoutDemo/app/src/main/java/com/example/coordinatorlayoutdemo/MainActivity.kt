package com.example.coordinatorlayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coordinatorlayoutdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : Adapter

    val itemList = listOf<Item>(
        Item("Mango", false),
        Item("Apple", false),
        Item("Banana", false),
        Item("Guava", false),
        Item("Lemon", false),
        Item("Pear", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false),
        Item("Orange", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = Adapter() {
            listItemClicked(it)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.updateList(itemList)

    }

    private fun listItemClicked(item: Item){
        Toast.makeText(
            this@MainActivity,
            "Supplier is : ${item.memo}",
            Toast.LENGTH_SHORT
        ).show()
    }
}