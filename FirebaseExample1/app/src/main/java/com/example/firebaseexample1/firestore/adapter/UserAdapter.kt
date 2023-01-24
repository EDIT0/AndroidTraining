package com.example.firebaseexample1.firestore.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample1.R
import com.example.firebaseexample1.firestore.model.UserModel

class UserAdapter(
    private val clickListener:(UserModel)->Unit
) : RecyclerView.Adapter<UserViewHolder>() {

    private var list : ArrayList<UserModel> = ArrayList<UserModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.user_item, parent, false)

        return UserViewHolder(item)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(userModels: List<UserModel>) {
        if(userModels.isEmpty()) {
            Log.i("MYTAG", "empty")
            this.notifyDataSetChanged()
        }
        userModels?.let {
            val diffCallback = ItemDiffUtilCallback(this.list, userModels)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.list.run {
                clear()
                addAll(userModels)
                diffResult.dispatchUpdatesTo(this@UserAdapter)
            }
        }
    }


}

class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(userModel : UserModel, clickListener:(UserModel)->Unit) {
        val textView = view.findViewById<TextView>(R.id.textView)

        val name = userModel.name
        val email = userModel.email
        val tel = userModel.tel
        val date = userModel.date
        textView.text = "${email}\n${name}\n${tel}\n${date}"

        view.setOnClickListener {
            clickListener(userModel)
        }
    }

}