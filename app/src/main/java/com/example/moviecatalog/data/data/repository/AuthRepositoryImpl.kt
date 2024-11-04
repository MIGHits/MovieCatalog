package com.example.moviecatalog.data.data.repository

import com.example.moviecatalog.data.data.mappers.LoginCredentialsMapper
import com.example.moviecatalog.data.data.mappers.UserMapper
import com.example.moviecatalog.data.data.remote.entities.LoginCredentialsDTO
import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO
import com.example.moviecatalog.data.data.remote.services.AuthService
import com.example.moviecatalog.data.data.storage.TokenStorage
import com.example.moviecatalog.domain.entity.LoginBody
import com.example.moviecatalog.domain.entity.UserRegisterModel
import com.example.moviecatalog.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val tokenStorage: TokenStorage,
    private val authApi: AuthService,
    private val userMapper: UserMapper,
    private val loginMapper: LoginCredentialsMapper
) : AuthRepository {

    override suspend fun register(user: UserRegisterModel) {
        val token = authApi.register(userMapper.map(user))
        tokenStorage.saveToken(token)
    }

    override suspend fun login(loginCredentials: LoginBody) {
        val token = authApi.login(loginMapper.map(loginCredentials))
        tokenStorage.saveToken(token)
    }

    override suspend fun logout() {
        authApi.logout()
        tokenStorage.removeToken()
    }
}