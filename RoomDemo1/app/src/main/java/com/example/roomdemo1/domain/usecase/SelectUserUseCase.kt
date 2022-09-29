package com.example.roomdemo1.domain.usecase

import com.example.roomdemo1.data.model.User
import com.example.roomdemo1.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SelectUserUseCase(
    private val userRepository: UserRepository
) {
    fun execute(name: String) : Flow<List<User>> {
        return userRepository.selectUserUsingName(name)
    }
}