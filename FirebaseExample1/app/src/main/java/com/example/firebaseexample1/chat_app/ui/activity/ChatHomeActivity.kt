package com.example.firebaseexample1.chat_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseexample1.R
import com.example.firebaseexample1.chat_app.ui.fragment.ChatPeopleFragment
import com.example.firebaseexample1.databinding.ActivityChatHomeBinding

class ChatHomeActivity : AppCompatActivity() {

    private val TAG = ChatHomeActivity::class.java.simpleName

    private lateinit var binding : ActivityChatHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, ChatPeopleFragment())
            .commitAllowingStateLoss()

    }
}