package com.example.databindingdemo2

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.databindingdemo2.viewmodel.TextRecyclerViewModel

class ListenerEvent(
    private val context: Context
) {
    fun onClickListener(viewModel: TextRecyclerViewModel) {
        if(!getLogin()) {
            Toast.makeText(context, "로그인 필요", Toast.LENGTH_SHORT).show()
            return
        }
        Log.i("MYTAG", "${viewModel.textList.value} from ListenerEvent")
        context.startActivity(Intent(context, SecondActivity::class.java))
    }

    private fun getLogin() : Boolean {
        return false
    }
}