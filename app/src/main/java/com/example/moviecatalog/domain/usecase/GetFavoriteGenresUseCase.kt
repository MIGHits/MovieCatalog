package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteGenresUseCase(private val repository: DatabaseRepository) {
    suspend operator fun invoke(userId: String): Flow<List<GenreModel>> {
        return repository.getFavoriteGenres(userId)
    }
}