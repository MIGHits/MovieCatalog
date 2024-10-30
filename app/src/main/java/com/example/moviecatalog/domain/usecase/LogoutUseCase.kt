package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.LoginBody
import com.example.moviecatalog.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}