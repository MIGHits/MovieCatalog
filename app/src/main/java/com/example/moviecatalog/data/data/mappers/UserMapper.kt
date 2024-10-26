package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO
import com.example.moviecatalog.domain.entity.UserRegisterModel

class UserMapper {
    fun map (user:UserRegisterModel):UserRegisterModelDTO{
        return UserRegisterModelDTO(
            userName = user.userName,
            name = user.name,
            password = user.password,
            email = user.email,
            birthDate = user.birthDate,
            gender = user.gender
        )
    }
}