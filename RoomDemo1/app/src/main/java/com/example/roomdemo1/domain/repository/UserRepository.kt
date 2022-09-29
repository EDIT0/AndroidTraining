package com.example.roomdemo1.domain.repository

import androidx.lifecycle.LiveData
import com.example.roomdemo1.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAllUser() : Flow<List<User>>
    suspend fun addUser(user: User) : Flow<Long>
    suspend fun deleteUserUsingId(id: Int) : Flow<Int>
    suspend fun updateUserUsingId(id: Int, newName: String) : Flow<Int>
    fun selectUserUsingName(name: String) : Flow<List<User>>
}