package com.example.coordinatorlayourdemo6

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coordinatorlayourdemo6.databinding.ActivityMainBinding
import com.example.coordinatorlayoutdemo6.Adapter
import com.example.coordinatorlayoutdemo6.Item
import com.google.android.material.appbar.AppBarLayout

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


        binding.appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                Log.i("MYTAG", "value : ${Math.abs(verticalOffset)-appBarLayout!!.getTotalScrollRange()}")
//                if (Math.abs(verticalOffset) - appBarLayout!!.getTotalScrollRange() == 0)
                if (Math.abs(verticalOffset) - appBarLayout!!.getTotalScrollRange() > -100) {
                    //  Collapsed
                    Log.i("MYTAG", "Collapsed")
                    binding.ivBack.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.white))
                    binding.ivShare.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.white))
                    binding.ivLike.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.red))
                    binding.tvTitle.visibility = View.VISIBLE
                    binding.tvTitle.setTextColor(Color.parseColor("#ffffff"))
//                    binding.toolbar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.black))

                }
                else {
                    //Expanded
                    Log.i("MYTAG", "Expanded")
                    binding.ivBack.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.black))
                    binding.ivShare.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.black))
                    binding.ivLike.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.black))
                    binding.tvTitle.visibility = View.INVISIBLE
                    binding.tvTitle.setTextColor(Color.parseColor("#000000"))
//                    binding.toolbar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.white))

                }
            }
        })

    }

    private fun listItemClicked(item: Item){
        Toast.makeText(
            this@MainActivity,
            "Supplier is : ${item.memo}",
            Toast.LENGTH_SHORT
        ).show()
    }
}