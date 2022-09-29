package com.example.roomdemo1.data.repository

import androidx.lifecycle.LiveData
import com.example.roomdemo1.data.model.User
import com.example.roomdemo1.data.repository.local.UserLocalDataSource
import com.example.roomdemo1.data.util.ERROR
import com.example.roomdemo1.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository{
    override fun getAllUser(): Flow<List<User>> {
        return flow {
            userLocalDataSource.getAllUser()
                .collect {
                    emit(it)
                }
        }
    }

    override suspend fun addUser(user: User): Flow<Long> {
        return flow {
            val result = userLocalDataSource.addUser(user)
            emit(result)
        }
    }

    override suspend fun deleteUserUsingId(id: Int): Flow<Int> {
        return flow {
            val result = userLocalDataSource.deleteUserUsingId(id)
            emit(result)
        }
    }

    override suspend fun updateUserUsingId(id: Int, newName: String): Flow<Int> {
        return flow {
            val result = userLocalDataSource.updateUserUsingId(id, newName)
            emit(result)
        }
    }

    override fun selectUserUsingName(name: String): Flow<List<User>> {
        return flow {
            userLocalDataSource.selectUserUsingName(name)
                .collect {
                    if(it.isEmpty()) {
                        throw Exception(ERROR)
                    } else {
                        emit(it)
                    }
                }
        }
    }
}