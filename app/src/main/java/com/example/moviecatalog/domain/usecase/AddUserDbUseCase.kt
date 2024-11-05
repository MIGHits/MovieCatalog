package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.UserShortModel
import com.example.moviecatalog.domain.repository.DatabaseRepository

class AddUserDbUseCase(private val repository: DatabaseRepository) {
    suspend operator fun invoke(user: UserShortModel) {
        repository.addUser(user)
    }
}