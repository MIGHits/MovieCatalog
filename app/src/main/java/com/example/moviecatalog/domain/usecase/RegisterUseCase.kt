package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO
import com.example.moviecatalog.domain.repository.AuthRepository
import com.example.moviecatalog.presentation.entity.UserRegisterUIModel

class RegisterUseCase(private val authRepository: AuthRepository){
    suspend operator fun invoke(registerCredentials: UserRegisterUIModel){
        authRepository.register(
            UserRegisterModelDTO(
                userName = registerCredentials.userName,
                name = registerCredentials.name,
                password = registerCredentials.password,
                email = registerCredentials.email,
                birthDate = registerCredentials.birthDate,
                gender = registerCredentials.gender
            )
        )
    }
}