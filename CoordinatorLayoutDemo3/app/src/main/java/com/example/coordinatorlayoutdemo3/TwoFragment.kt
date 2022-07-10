package com.example.coordinatorlayoutdemo3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coordinatorlayoutdemo3.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {
    private lateinit var fragmentTwoBinding: FragmentTwoBinding

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

        fragmentTwoBinding = FragmentTwoBinding.bind(inflater.inflate(R.layout.fragment_two, container, false))
        return fragmentTwoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Adapter()

        fragmentTwoBinding.recyclerView.layoutManager = LinearLayoutManager(fragmentTwoBinding.root.context)
        fragmentTwoBinding.recyclerView.adapter = adapter

        adapter.updateList(itemList)
    }
}