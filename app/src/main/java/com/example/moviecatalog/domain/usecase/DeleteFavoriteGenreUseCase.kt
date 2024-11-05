package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.repository.DatabaseRepository

class DeleteFavoriteGenreUseCase(private val repository: DatabaseRepository) {
    suspend operator fun invoke(userId: String, genreId: String) {
        repository.deleteFavoriteGenre(userId, genreId)
    }
}