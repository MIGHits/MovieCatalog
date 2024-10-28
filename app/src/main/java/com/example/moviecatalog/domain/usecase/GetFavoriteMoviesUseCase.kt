package com.example.moviecatalog.domain.usecase

import android.util.Log
import com.example.moviecatalog.domain.entity.MovieElementModel
import com.example.moviecatalog.domain.repository.FavoriteMovieRepository

class GetFavoriteMoviesUseCase(private val repository: FavoriteMovieRepository) {
    suspend operator fun invoke(): List<MovieElementModel>? {
        Log.d("Rep","true")
        return repository.getFavorites()
    }
}