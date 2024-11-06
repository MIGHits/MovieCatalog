package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.Friend
import com.example.moviecatalog.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetFriendsUseCase(private val repository: DatabaseRepository) {
    suspend operator fun invoke(userId: String): Flow<List<Friend>> {
        return repository.getFriendList(userId)
    }
}