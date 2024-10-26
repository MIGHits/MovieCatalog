package com.example.moviecatalog.domain.repository


import com.example.moviecatalog.domain.entity.LoginBody
import com.example.moviecatalog.domain.entity.UserRegisterModel



interface AuthRepository {
    suspend fun register(user: UserRegisterModel)

    suspend fun login(loginCredentials: LoginBody)

    suspend fun logout()
}