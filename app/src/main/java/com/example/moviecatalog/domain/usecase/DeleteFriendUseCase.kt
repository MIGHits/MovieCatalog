package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.repository.DatabaseRepository

class DeleteFriendUseCase(private val repository: DatabaseRepository) {
    suspend operator fun invoke(friendId: String, userId: String) {
        repository.deleteFriend(friendId, userId)
    }
}