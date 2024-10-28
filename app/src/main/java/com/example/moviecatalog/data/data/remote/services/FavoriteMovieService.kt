package com.example.moviecatalog.data.data.remote.services

import com.example.moviecatalog.data.data.remote.dataSource.URL.ADD_FAVORITES
import com.example.moviecatalog.data.data.remote.dataSource.URL.FAVORITES
import com.example.moviecatalog.data.data.remote.dataSource.URL.MOVIE_DETAILS
import com.example.moviecatalog.data.data.remote.dataSource.URL.MOVIE_PAGE
import com.example.moviecatalog.data.data.remote.dataSource.URL.REMOVE_FAVORITES
import com.example.moviecatalog.data.data.remote.entities.MovieDetailsModelDTO
import com.example.moviecatalog.data.data.remote.entities.MovieElementModelDTO
import com.example.moviecatalog.data.data.remote.entities.MoviesListModel
import com.example.moviecatalog.data.data.remote.entities.MoviesPagedListModelDTO
import com.example.moviecatalog.data.data.remote.entities.Token
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteMovieService {
    @GET(FAVORITES)
    suspend fun getFavorites(
        @Header("Authorization")token: String):MoviesListModel

    @POST(ADD_FAVORITES)
    suspend fun addFavoriteMovie(
        @Path("id") id:String,
        @Header("Authorization")token: String)

    @DELETE(REMOVE_FAVORITES)
    suspend fun removeFavoriteMovie(
        @Path("id") id:String,
        @Header("Authorization")token: String)
}