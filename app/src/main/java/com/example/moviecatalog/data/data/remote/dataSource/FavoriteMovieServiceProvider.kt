package com.example.moviecatalog.data.data.remote.dataSource

import android.util.Log
import com.example.moviecatalog.data.data.remote.dataSource.RetrofitClientProvide.retrofitProvider
import com.example.moviecatalog.data.data.remote.entities.MovieElementModelDTO
import com.example.moviecatalog.data.data.remote.entities.MoviesListModel
import com.example.moviecatalog.data.data.remote.entities.Token
import com.example.moviecatalog.data.data.remote.services.FavoriteMovieService
import com.example.moviecatalog.data.data.remote.services.MovieService

class FavoriteMovieServiceProvider:FavoriteMovieService {
    private val movieServiceProvider = retrofitProvider.create(FavoriteMovieService::class.java)
    override suspend fun getFavorites(token: String): MoviesListModel {
        return movieServiceProvider.getFavorites(token)
    }


    override suspend fun addFavoriteMovie(id: String, token: String) {
        movieServiceProvider.addFavoriteMovie(id,token)
    }

    override suspend fun removeFavoriteMovie(id: String, token: String){
        movieServiceProvider.removeFavoriteMovie(id,token)
    }

}