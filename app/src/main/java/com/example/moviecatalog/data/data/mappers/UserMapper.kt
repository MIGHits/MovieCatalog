package com.example.moviecatalog.data.data.mappers
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.data.data.remote.entities.UserRegisterModelDTO
import com.example.moviecatalog.data.data.remote.entities.UserShortModelDTO
import com.example.moviecatalog.domain.entity.UserRegisterModel
import com.example.moviecatalog.domain.entity.UserShortModel

class UserMapper {
    fun map(user: UserRegisterModel): UserRegisterModelDTO {
        return UserRegisterModelDTO(
            userName = user.userName,
            name = user.name,
            password = user.password,
            email = user.email,
            birthDate = user.birthDate,
            gender = user.gender
        )
    }

    fun map(user:UserShortModelDTO?):UserShortModel{
        return UserShortModel(
            userId = user?.userId ?: INITIAL_FIELD_STATE,
            nickName = user?.nickName,
            avatar = user?.avatar
        )
    }
}