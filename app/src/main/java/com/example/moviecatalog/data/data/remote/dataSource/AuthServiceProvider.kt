package com.example.moviecatalog.data.data.remote.dataSource

import com.example.moviecatalog.data.data.remote.dataSource.RetrofitClientProvide.retrofitProvider
import com.example.moviecatalog.data.data.remote.entities.LoginCredentialsDTO
import com.example.moviecatalog.data.data.remote.entities.Token
import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO
import com.example.moviecatalog.data.data.remote.services.AuthService

class AuthServiceProvider() : AuthService {
    private val authServiceProvider = retrofitProvider.create(AuthService::class.java)

    override suspend fun register(registerCredentials: UserRegisterModelDTO): Token {
        return authServiceProvider.register(registerCredentials)
    }

    override suspend fun login(loginCredentials: LoginCredentialsDTO): Token {
        return authServiceProvider.login(loginCredentials)
    }

    override suspend fun logout() {
        authServiceProvider.logout()
    }
}