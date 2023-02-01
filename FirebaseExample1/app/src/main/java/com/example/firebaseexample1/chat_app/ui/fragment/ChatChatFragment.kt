package com.example.firebaseexample1.chat_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaseexample1.R
import com.example.firebaseexample1.databinding.FragmentChatChatBinding
import com.example.firebaseexample1.databinding.FragmentChatPeopleBinding


class ChatChatFragment : Fragment() {

    private var _binding: FragmentChatChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatChatBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
//        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}