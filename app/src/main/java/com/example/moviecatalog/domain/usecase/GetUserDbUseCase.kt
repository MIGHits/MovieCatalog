package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.UserShortModel
import com.example.moviecatalog.domain.repository.DatabaseRepository

class GetUserDbUseCase(private val repository: DatabaseRepository) {
    suspend operator fun invoke(): List<UserShortModel> {
        return repository.getUsers()
    }
}