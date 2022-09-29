package com.example.roomdemo1.data.repository.local

import androidx.lifecycle.LiveData
import com.example.roomdemo1.data.db.UserDao
import com.example.roomdemo1.data.model.User
import kotlinx.coroutines.flow.Flow

class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource {
    override fun getAllUser(): Flow<List<User>> {
        return userDao.getAllUser()
    }

    override suspend fun addUser(user: User): Long {
        return userDao.insert(user)
    }

    override suspend fun deleteUserUsingId(id: Int): Int {
        return userDao.deleteUsingId(id)
    }

    override suspend fun updateUserUsingId(id: Int, newName: String): Int {
        return userDao.updateUsingId(id, newName)
    }

    override fun selectUserUsingName(name: String): Flow<List<User>> {
        return userDao.selectUsingName(name)
    }

}