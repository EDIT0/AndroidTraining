package com.example.firebaseexample1.firestore

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample1.R

class Adapter(
    private val clickListener:(Item)->Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var list : ArrayList<Item> = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item, parent, false)

        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(items: List<Item>) {
        if(items.isEmpty()) {
            Log.i("MYTAG", "empty")
            this.notifyDataSetChanged()
        }
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

    fun bind(item : Item, clickListener:(Item)->Unit) {
        val textView = view.findViewById<TextView>(R.id.textView)

        val name = item.name
        val email = item.email
        val tel = item.tel
        val date = item.date
        textView.text = "${email}\n${name}\n${tel}\n${date}"

        view.setOnClickListener {
            clickListener(item)
        }
    }

}