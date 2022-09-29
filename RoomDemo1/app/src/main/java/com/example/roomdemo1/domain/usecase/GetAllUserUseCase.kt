package com.example.roomdemo1.domain.usecase

import androidx.lifecycle.LiveData
import com.example.roomdemo1.data.model.User
import com.example.roomdemo1.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUserUseCase(
    private val userRepository: UserRepository
) {
    fun execute() : Flow<List<User>> {
        return userRepository.getAllUser()
    }
}