package com.example.moviecatalog.data.data.remote.services

import com.example.moviecatalog.data.data.remote.dataSource.URL.LOGIN
import com.example.moviecatalog.data.data.remote.dataSource.URL.LOGOUT
import com.example.moviecatalog.data.data.remote.dataSource.URL.REGISTER
import com.example.moviecatalog.data.data.remote.entities.LoginCredentialsDTO
import com.example.moviecatalog.data.data.remote.entities.Token
import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(REGISTER)
    suspend fun register(@Body registerCredentials:UserRegisterModelDTO):Token
    @POST(LOGIN)
    suspend fun login(@Body loginCredentials:LoginCredentialsDTO):Token
    @POST(LOGOUT)
    suspend fun logout()

}