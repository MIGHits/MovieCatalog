package com.example.moviecatalog.domain.usecase.Validation

import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO
import com.example.moviecatalog.domain.entity.UserRegisterModel
import com.example.moviecatalog.domain.repository.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository){
    operator suspend fun invoke(registerCredentials: UserRegisterModel){
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