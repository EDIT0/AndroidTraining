package com.example.firebaseexample1.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseexample1.databinding.ActivityFireStoreHomeBinding
import com.example.firebaseexample1.databinding.ActivityMainBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireStoreHomeActivity : AppCompatActivity() {

    private val TAG = FireStoreHomeActivity::class.java.simpleName

    private lateinit var binding : ActivityFireStoreHomeBinding

    private lateinit var adapter : Adapter

    val db = FirebaseFirestore.getInstance()
    private val userList = mutableListOf<Item>()

    private var date : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFireStoreHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setRecyclerView()
        getData()
        snapShotListener()
        buttonClickListener()


    }

    private fun setRecyclerView() {
        adapter = Adapter() {
            val document = db.collection("test_db").document()
            val documentID = document.id
            Log.i(TAG, "document id: ${documentID}")

            db.collection("test_db")
                .whereEqualTo("date", it.date)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")

                        db.collection("test_db")
                            .document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot successfully deleted! ${it}")
                                userList.remove(
                                    Item(
                                        document.data["email"].toString(),
                                        document.data["name"].toString(),
                                        document.data["tel"].toString().toLong(),
                                        document.data["date"].toString().toLong()
                                    )
                                )
                                adapter.updateList(userList)
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error deleting document", e)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }

        binding.rvData.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvData.adapter = adapter
    }

    private fun getData() {
        db.collection("test_db")
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener  { result ->
//                binding.tvData.text = ""
                for(document in result) {
                    Log.i(TAG, "${document.data}")
//                    binding.tvData.append(document.data.toString() + "\n")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun snapShotListener() {
        val docRef = db.collection("test_db").orderBy("date", Query.Direction.ASCENDING)
        docRef.addSnapshotListener { value, error ->
            if(error != null) {
                Log.w(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            if(value?.isEmpty == false) {
                for (dc in value.documentChanges) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        Log.d(TAG, "New User: ${dc.document.data}")
//                        userList.add(
//                            Item(
//                                dc.document.data["email"].toString(),
//                                dc.document.data["name"].toString(),
//                                dc.document.data["tel"].toString().toLong(),
//                                dc.document.data["date"].toString().toLong()
//                            )
//                        )
//                        adapter.updateList(userList)
                    }
                    if(dc.type == DocumentChange.Type.REMOVED) {
                        Log.d(TAG, "Remove User: ${dc.document.data}")
//                        userList.remove(
//                            Item(
//                                dc.document.data["email"].toString(),
//                                dc.document.data["name"].toString(),
//                                dc.document.data["tel"].toString().toLong(),
//                                dc.document.data["date"].toString().toLong()
//                            )
//                        )
//                        adapter.updateList(userList)
                    }
                    if(dc.type == DocumentChange.Type.MODIFIED) {
                        Log.d(TAG, "Modified User: ${dc.document.data}")
                    }
                }
            } else {
                adapter.updateList(userList)
            }
        }
    }

    private fun buttonClickListener() {
        binding.btnInputData.setOnClickListener {
            binding.apply {
                if(etEmail.text.isEmpty() || etName.text.isEmpty() || etTel.text.isEmpty()) {
                    Toast.makeText(binding.root.context, "검색어가 비었습니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            date = System.currentTimeMillis()
            val user = hashMapOf(
                "email" to "${binding.etEmail.text}",
                "name" to "${binding.etName.text}",
                "tel" to binding.etTel.text.toString().toLong(),
                "date" to date
            )

            // Add a new document with a generated ID
            db.collection("test_db")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    userList.add(
                        Item(
                            binding.etEmail.text.toString(),
                            binding.etName.text.toString(),
                            binding.etTel.text.toString().toLong(),
                            date
                        )
                    )
                    adapter.updateList(userList)
                    binding.apply {
                        etName.setText("")
                        etEmail.setText("")
                        etTel.setText("")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }
}