package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.local.entities.User
import com.example.moviecatalog.domain.entity.UserShortModel

class DbUserMapper {
    fun map(user: User): UserShortModel {
        return UserShortModel(
            userId = user.id,
            nickName = user.nickname,
            avatar = null
        )
    }

    fun map(list: List<User>): List<UserShortModel> {
        return list.map { map(it) }
    }
}