package com.example.coordinatorlayourdemo7

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.coordinatorlayourdemo7.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout


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



        binding.appBar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                Log.d("STATE", state.name)
                if(state.name == "EXPANDED") {
                    binding.tvHome.setTextColor(Color.parseColor("#000000"))
                    Glide
                        .with(binding.ibShare.context)
                        .load(R.drawable.ic_baseline_share_24_black)
                        .into(binding.ibShare)
                    Glide
                        .with(binding.ibSend.context)
                        .load(R.drawable.ic_baseline_send_24_black)
                        .into(binding.ibSend)
                    binding.tvText2.visibility = View.VISIBLE
                    binding.ibNotification.visibility = View.VISIBLE
                } else if(state.name == "COLLAPSED") {
                    binding.tvHome.setTextColor(Color.parseColor("#ffffff"))
                    Glide
                        .with(binding.ibShare.context)
                        .load(R.drawable.ic_baseline_share_24_white)
                        .into(binding.ibShare)
                    Glide
                        .with(binding.ibSend.context)
                        .load(R.drawable.ic_baseline_send_24_white)
                        .into(binding.ibSend)
                    binding.tvText2.visibility = View.GONE
                    binding.ibNotification.visibility = View.GONE
                }
            }
        })




//        val toolbar: Toolbar = binding.toolbar
//        setSupportActionBar(toolbar)
//        val toolBarLayout: CollapsingToolbarLayout = binding.toolbarLayout
//        toolBarLayout.title = title
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_actionbar, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//    }
}