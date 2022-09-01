package com.example.coordinatorlayoutdemo3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coordinatorlayoutdemo3.databinding.FragmentOneBinding
import com.example.coordinatorlayoutdemo3.databinding.FragmentThreeBinding

class ThreeFragment : Fragment() {
    private val TAG = ThreeFragment::class.java.simpleName

    private lateinit var fragmentThreeBinding: FragmentThreeBinding

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

        fragmentThreeBinding = FragmentThreeBinding.bind(inflater.inflate(R.layout.fragment_three, container, false))
        return fragmentThreeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Adapter()

        fragmentThreeBinding.recyclerView.layoutManager = LinearLayoutManager(fragmentThreeBinding.root.context)
        fragmentThreeBinding.recyclerView.adapter = adapter

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