package com.example.firebaseexample1.firestore.model

data class UserModel (
    val uid: String,
    val email : String,
    val name: String,
    val tel: Long,
    val date: Long
) {
}