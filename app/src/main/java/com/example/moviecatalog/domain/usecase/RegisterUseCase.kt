package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.UserRegisterModel
import com.example.moviecatalog.domain.repository.AuthRepository


class RegisterUseCase(private val authRepository: AuthRepository){
    suspend operator fun invoke(registerCredentials: UserRegisterModel){
        authRepository.register(registerCredentials)
    }
}