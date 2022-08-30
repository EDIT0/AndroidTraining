package com.example.concatadapterstudy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.concatadapterstudy.BaseConcatHolder
import com.example.concatadapterstudy.R

class BaseHorizontalConcatAdapter(private val context: Context, private val horizontalAdapter: HorizontalAdapter) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val view = LayoutInflater.from(context).inflate(R.layout.horizontal_concat_row, parent,false)
        view.findViewById<RecyclerView>(R.id.rv_horizontal_concat).layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

//        view.rv_horizontal_concat.layoutManager = GridLayoutManager(context, spanCount)
        return ConcatViewHolder(view)
    }

    override fun getItemCount(): Int  = 1

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder -> holder.bind(horizontalAdapter)
            else -> throw IllegalArgumentException("No viewholder to show this data, did you forgot to add it to the onBindViewHolder?")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.horizontal_list_item
    }

    inner class ConcatViewHolder(itemView: View): BaseConcatHolder<HorizontalAdapter>(itemView){
        override fun bind(adapter: HorizontalAdapter) {
            itemView.findViewById<RecyclerView>(R.id.rv_horizontal_concat).adapter = adapter
        }
    }
}