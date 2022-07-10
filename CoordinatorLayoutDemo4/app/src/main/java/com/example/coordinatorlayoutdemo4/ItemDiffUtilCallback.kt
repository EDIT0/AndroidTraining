//package com.example.coordinatorlayoutdemo4
//
//import androidx.recyclerview.widget.DiffUtil
//
//class ItemDiffUtilCallback(private val oldList: List<Any>, private val newList: List<Any>) : DiffUtil.Callback() {
//
//    override fun getOldListSize(): Int = oldList.size
//
//    override fun getNewListSize(): Int = newList.size
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldItem = oldList[oldItemPosition]
//        val newItem = newList[newItemPosition]
//
//        return if (oldItem is Item && newItem is Item) {
//            oldItem.memo == newItem.memo
//        } else {
//            false
//        }
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
//        oldList[oldItemPosition] == newList[newItemPosition]
//}