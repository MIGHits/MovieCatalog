package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.data.data.remote.entities.UserShortModelDTO
import com.example.moviecatalog.domain.entity.Friend
import com.example.moviecatalog.domain.entity.UserShortModel
import com.example.moviecatalog.presentation.entity.UserShortModelUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserMapper {
    fun map(user: UserShortModel): UserShortModelUI {
        return UserShortModelUI(
            userId = user.userId,
            nickName = user.nickName,
            avatar = user.avatar
        )
    }

    fun reverseMap(user: UserShortModelUI): UserShortModel {
        return UserShortModel(
            userId = user.userId,
            nickName = user.nickName,
            avatar = user.avatar
        )
    }

    fun dbUsers(list: List<UserShortModel>): List<UserShortModelUI> {
        return list.map { map(it) }
    }

    fun friendMap(friend: Friend): UserShortModelUI {
        return UserShortModelUI(
            userId = friend.id,
            nickName = friend.nickname,
            avatar = friend.avatar
        )
    }

    fun friendsFlow(friendList: Flow<List<Friend>>): Flow<List<UserShortModelUI>> {
        return friendList.map { friend -> friend.map { friendMap(it) } }
    }
}