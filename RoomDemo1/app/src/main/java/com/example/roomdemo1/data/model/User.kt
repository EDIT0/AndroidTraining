package com.example.roomdemo1.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    var name:String,
    var age: Int,
    var major: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}