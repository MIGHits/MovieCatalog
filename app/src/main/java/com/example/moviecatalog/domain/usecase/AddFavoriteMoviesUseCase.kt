package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.data.data.repository.FavoriteMoviesRepositoryImpl

class AddFavoriteMoviesUseCase(private val repository: FavoriteMoviesRepositoryImpl){
    suspend operator fun invoke(id:String) {
        repository.addFavorites(id)
    }
}