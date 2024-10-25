package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.data.remote.entities.LoginCredentialsDTO
import com.example.moviecatalog.domain.entity.LoginBody
import com.example.moviecatalog.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(loginCredentials: LoginBody){
        authRepository.login(
            LoginCredentialsDTO(
                username =  loginCredentials.username,
                password =  loginCredentials.password
            )
        )
    }
}