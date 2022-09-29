package com.example.roomdemo1.data.repository.local

import androidx.lifecycle.LiveData
import com.example.roomdemo1.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getAllUser() : Flow<List<User>>
    suspend fun addUser(user: User) : Long
    suspend fun deleteUserUsingId(id: Int) : Int
    suspend fun updateUserUsingId(id: Int, newName: String) : Int
    fun selectUserUsingName(name: String) : Flow<List<User>>
}