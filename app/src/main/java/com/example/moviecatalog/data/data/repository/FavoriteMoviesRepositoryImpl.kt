package com.example.moviecatalog.data.data.repository

import android.util.Log
import com.example.moviecatalog.data.data.mappers.MovieModelMapper
import com.example.moviecatalog.data.data.remote.services.FavoriteMovieService
import com.example.moviecatalog.data.data.storage.TokenStorage
import com.example.moviecatalog.domain.entity.MovieElementModel
import com.example.moviecatalog.domain.repository.FavoriteMovieRepository

class FavoriteMoviesRepositoryImpl(
    private val tokenStorage: TokenStorage,
    private val favoritesApi: FavoriteMovieService,
    private val movieMapper: MovieModelMapper = MovieModelMapper()
) : FavoriteMovieRepository {


    override suspend fun getFavorites(): List<MovieElementModel>? {
        return favoritesApi.getFavorites(tokenStorage.getToken().token).movies?.let {
            movieMapper.map(
                it
            )
        }
    }

    override suspend fun addFavorites(id: String) {
        favoritesApi.addFavoriteMovie(id, tokenStorage.getToken().token)
    }

    override suspend fun deleteFavorites(id: String) {
        favoritesApi.removeFavoriteMovie(id, tokenStorage.getToken().token)
    }

}