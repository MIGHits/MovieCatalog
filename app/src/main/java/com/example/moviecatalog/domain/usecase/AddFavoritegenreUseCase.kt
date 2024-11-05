package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.domain.repository.DatabaseRepository

class AddFavoritegenreUseCase(private val repository: DatabaseRepository) {
    suspend operator fun invoke(genre: GenreModel, userId: String) {
        repository.addFavoriteGenre(genre, userId)
    }
}