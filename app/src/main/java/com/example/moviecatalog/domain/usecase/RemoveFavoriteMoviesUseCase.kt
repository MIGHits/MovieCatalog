package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.repository.FavoriteMovieRepository

class RemoveFavoriteMoviesUseCase(private val repository: FavoriteMovieRepository) {
    suspend operator fun invoke(id:String){
        repository.deleteFavorites(id)
    }
}