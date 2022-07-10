//package com.example.coordinatorlayoutdemo4
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.coordinatorlayoutdemo4.databinding.FragmentOneBinding
//
//
//class OneFragment : Fragment() {
//
//    private lateinit var fragmentOneBinding: FragmentOneBinding
//
//    private lateinit var adapter : Adapter
//
//    val itemList = listOf<Item>(
//        Item("Mango"),
//        Item("Apple"),
//        Item("Banana"),
//        Item("Guava"),
//        Item("Lemon"),
//        Item("Pear"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Orange"),
//        Item("Mango"),
//        Item("Apple"),
//        Item("Banana"),
//        Item("Guava"),
//        Item("Lemon"),
//        Item("Pear"),
//        Item("Mango"),
//        Item("Apple"),
//        Item("Banana"),
//        Item("Guava"),
//        Item("Lemon"),
//        Item("Pear"),
//        Item("Orange")
//    )
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//
//        fragmentOneBinding = FragmentOneBinding.bind(inflater.inflate(R.layout.fragment_one, container, false))
//        return fragmentOneBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        adapter = Adapter()
//
//        fragmentOneBinding.recyclerView.layoutManager = LinearLayoutManager(fragmentOneBinding.root.context)
//        fragmentOneBinding.recyclerView.adapter = adapter
//
//        adapter.updateList(itemList)
//    }
//
//
//}