package com.example.roomdemo1.domain.usecase

import com.example.roomdemo1.data.model.User
import com.example.roomdemo1.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class AddUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(user: User) : Flow<Long> {
        return userRepository.addUser(user)
    }
}