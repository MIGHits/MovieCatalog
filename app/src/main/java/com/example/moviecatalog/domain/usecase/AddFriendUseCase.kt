package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.Friend
import com.example.moviecatalog.domain.repository.DatabaseRepository

class AddFriendUseCase(private val repository: DatabaseRepository) {
    suspend operator fun invoke(friend: Friend, userId: String) {
        repository.addFriend(friend, userId)
    }
}