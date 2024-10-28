package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.entity.MovieElementModel

interface FavoriteMovieRepository {
    suspend fun getFavorites(): List<MovieElementModel>?
    suspend fun addFavorites(id:String)
    suspend fun deleteFavorites(id:String)
}