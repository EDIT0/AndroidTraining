package com.example.firebaseexample1.firestore.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample1.R
import com.example.firebaseexample1.firestore.model.MemoModel

class MemoAdapter(
    private val clickListener:(MemoModel)->Unit
) : RecyclerView.Adapter<MemoViewHolder>() {

    private var list : ArrayList<MemoModel> = ArrayList<MemoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.memo_item, parent, false)

        return MemoViewHolder(item)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(memoModels: List<MemoModel>) {
        if(memoModels.isEmpty()) {
            Log.i("MYTAG", "empty")
            this.notifyDataSetChanged()
        }

        memoModels?.let {
            val diffCallback = ItemDiffUtilCallback(this.list, memoModels)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.list.run {
                clear()
                addAll(memoModels)
                diffResult.dispatchUpdatesTo(this@MemoAdapter)
            }
        }
    }


}

class MemoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(memoModel : MemoModel, clickListener:(MemoModel)->Unit) {

        // todo 메모 view
        val tvMemo = view.findViewById<TextView>(R.id.tvMemo)
        tvMemo.text = "${memoModel.textMemo}\n${memoModel.date}\n${memoModel.uid}"
//        val textView = view.findViewById<TextView>(R.id.textView)
//
//        val name = userModel.name
//        val email = userModel.email
//        val tel = userModel.tel
//        val date = userModel.date
//        textView.text = "${email}\n${name}\n${tel}\n${date}"

        view.setOnClickListener {
            clickListener(memoModel)
        }
    }

}