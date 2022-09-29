package com.example.roomdemo1.domain.usecase

import com.example.roomdemo1.data.model.User
import com.example.roomdemo1.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(id: Int) : Flow<Int> {
        return userRepository.deleteUserUsingId(id)
    }
}