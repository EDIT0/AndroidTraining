package com.example.firebaseexample1.firestore.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.firebaseexample1.firestore.model.UserModel

class ItemDiffUtilCallback(private val oldList: List<Any>, private val newList: List<Any>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is UserModel && newItem is UserModel) {
            oldItem.date == newItem.date
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}