package com.example.coordinatorlayoutdemo6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinatorlayourdemo6.R

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

    override fun getItemViewType(position: Int): Int {
        return position
    }


}

class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item : Item, clickListener:(Item)->Unit) {
        val textView = view.findViewById<TextView>(R.id.textView)

        textView.text = item.memo

        view.setOnClickListener {
            clickListener(item)
        }

        val imageButtonView = view.findViewById<ImageButton>(R.id.imageButtonView)
        val layoutExpand = view.findViewById<LinearLayout>(R.id.layout_expand)

        imageButtonView.setOnClickListener {
            // 1
            val show = toggleLayout(!item.isExpanded, view, layoutExpand)
            item.isExpanded = show
        }
    }

    private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
        // 2
        ToggleAnimation.toggleArrow(view, isExpanded)
        if (isExpanded) {
            ToggleAnimation.expand(layoutExpand)
        } else {
            ToggleAnimation.collapse(layoutExpand)
        }
        return isExpanded
    }

}