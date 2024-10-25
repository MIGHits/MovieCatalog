package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.data.data.remote.entities.LoginCredentialsDTO
import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO


interface AuthRepository {
    suspend fun register(user: UserRegisterModelDTO)

    suspend fun login(loginCredentials: LoginCredentialsDTO)

    suspend fun logout()
}