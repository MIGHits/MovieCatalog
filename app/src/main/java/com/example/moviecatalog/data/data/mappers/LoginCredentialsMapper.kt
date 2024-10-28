package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.LoginCredentialsDTO
import com.example.moviecatalog.domain.entity.LoginBody

class LoginCredentialsMapper {
    fun map(loginBody: LoginBody): LoginCredentialsDTO {
        return LoginCredentialsDTO(
            username = loginBody.username,
            password = loginBody.password
        )
    }
}