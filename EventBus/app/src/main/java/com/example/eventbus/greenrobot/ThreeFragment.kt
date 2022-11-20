package com.example.eventbus.greenrobot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.eventbus.R

class ThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvCount).text = "0"

        buttonClickListener(view)
    }

    fun buttonClickListener(view: View) {
        view.findViewById<Button>(R.id.btnCreateEvent).setOnClickListener {
            val str = view.findViewById<TextView>(R.id.tvCount).text.toString()
            val count = if(str == "") 0 else str.toInt() + 1
            GreenRobotEventBus.postBus(GreenRobotEventBus.getGreenRobotObject, count.toString())
        }
    }
}