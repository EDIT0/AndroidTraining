package com.example.coordinatorlayoutdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class Adapter(

) : RecyclerView.Adapter<ViewHolder>() {

    private var list : ArrayList<Item> = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item, parent, false)

        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(items: List<Item>) {
        items?.let {
            val diffCallback = ItemDiffUtilCallback(this.list, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.list.run {
                clear()
                addAll(items)
                diffResult.dispatchUpdatesTo(this@Adapter)
            }
        }
    }


}

class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item : Item) {
        val textView = view.findViewById<TextView>(R.id.textView)

        textView.text = item.memo
    }

}