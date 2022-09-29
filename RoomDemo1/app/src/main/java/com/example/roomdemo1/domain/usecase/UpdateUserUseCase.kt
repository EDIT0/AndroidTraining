package com.example.roomdemo1.domain.usecase

import com.example.roomdemo1.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UpdateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(id: Int, newName: String) : Flow<Int> {
        return userRepository.updateUserUsingId(id, newName)
    }
}