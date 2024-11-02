package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.data.data.remote.entities.UserShortModelDTO
import com.example.moviecatalog.domain.entity.UserShortModel
import com.example.moviecatalog.presentation.entity.UserShortModelUI

class UserMapper {
    fun map(user: UserShortModel): UserShortModelUI {
        return UserShortModelUI(
            userId = user.userId,
            nickName = user.nickName,
            avatar = user.avatar
        )
    }
}