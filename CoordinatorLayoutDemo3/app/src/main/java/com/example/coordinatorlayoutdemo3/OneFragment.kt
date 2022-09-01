package com.example.coordinatorlayoutdemo3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coordinatorlayoutdemo3.databinding.FragmentOneBinding


class OneFragment : Fragment() {
    private val TAG = OneFragment::class.java.simpleName

    private lateinit var fragmentOneBinding: FragmentOneBinding

    private lateinit var adapter : Adapter

    private var isFirstStart = true

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
        Item("Orange"),
        Item("Mango"),
        Item("Apple"),
        Item("Banana"),
        Item("Guava"),
        Item("Lemon"),
        Item("Pear"),
        Item("Mango"),
        Item("Apple"),
        Item("Banana"),
        Item("Guava"),
        Item("Lemon"),
        Item("Pear"),
        Item("Orange")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentOneBinding = FragmentOneBinding.bind(inflater.inflate(R.layout.fragment_one, container, false))
        return fragmentOneBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Adapter()

        fragmentOneBinding.recyclerView.layoutManager = LinearLayoutManager(fragmentOneBinding.root.context)
        fragmentOneBinding.recyclerView.adapter = adapter

        Log.i(TAG, "onViewCreated()")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    // Fragment 보여질 때
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")

        if(isFirstStart) {
            Log.i(TAG, "Load Data!!")
            isFirstStart = false
            adapter.updateList(itemList)
        }
    }

    // Fragment 가려질 때
    override fun onPause() {
        super.onPause()

        Log.i(TAG, "onPause()")
    }


}