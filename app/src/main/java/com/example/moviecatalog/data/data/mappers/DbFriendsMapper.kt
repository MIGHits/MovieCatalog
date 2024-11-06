package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.local.entities.Friends
import com.example.moviecatalog.domain.entity.Friend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DbFriendsMapper {

    fun map(friend: Friends): Friend {
        return Friend(
            nickname = friend.nickname,
            id = friend.id,
            avatar = friend.avatar
        )
    }

    fun map(friendList: Flow<List<Friends>>): Flow<List<Friend>> {
        return friendList.map { data -> data.map { map(it) } }
    }
}