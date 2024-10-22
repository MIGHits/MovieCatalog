package com.example.moviecatalog.data.data.repository

import com.example.moviecatalog.data.data.remote.entities.LoginCredentialsDTO
import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO
import com.example.moviecatalog.data.data.remote.services.AuthService
import com.example.moviecatalog.data.data.storage.TokenStorage
import com.example.moviecatalog.domain.entity.UserRegisterModel
import com.example.moviecatalog.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val tokenStorage: TokenStorage,
    private val authApi:AuthService): AuthRepository {

    override suspend fun register(user: UserRegisterModelDTO){
       val token =  authApi.register(user)
        tokenStorage.saveToken(token.token)
    }

    override suspend fun login(loginCredentials:LoginCredentialsDTO){
        val token = authApi.login(loginCredentials)
        tokenStorage.saveToken(token.token)
    }

    override suspend fun logout() {
        tokenStorage.removeToken()
    }
}