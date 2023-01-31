package com.example.firebaseexample1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseexample1.chat_app.ChatLoginActivity
import com.example.firebaseexample1.databinding.ActivityFireStoreHomeBinding
import com.example.firebaseexample1.databinding.ActivityMainBinding
import com.example.firebaseexample1.firestore.FireStoreHomeActivity
import com.example.firebaseexample1.realtime_db.RealtimeHomeActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnFireStore.setOnClickListener {
            startActivity(Intent(binding.root.context, FireStoreHomeActivity::class.java))
        }

        binding.btnRealtimeDB.setOnClickListener {
            startActivity(Intent(binding.root.context, RealtimeHomeActivity::class.java))
        }

        binding.btnChatApp.setOnClickListener {
            startActivity(Intent(binding.root.context, ChatLoginActivity::class.java))
        }

    }
}