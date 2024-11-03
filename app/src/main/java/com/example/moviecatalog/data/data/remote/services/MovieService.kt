package com.example.moviecatalog.data.data.remote.services

import com.example.moviecatalog.data.data.remote.dataSource.URL.GET_MOVIE_DIRECTOR
import com.example.moviecatalog.data.data.remote.dataSource.URL.GET_MOVIE_RATINGS
import com.example.moviecatalog.data.data.remote.dataSource.URL.KP_TOKEN
import com.example.moviecatalog.data.data.remote.dataSource.URL.MOVIE_DETAILS
import com.example.moviecatalog.data.data.remote.dataSource.URL.MOVIE_PAGE
import com.example.moviecatalog.data.data.remote.entities.MovieDetailsModelDTO
import com.example.moviecatalog.data.data.remote.entities.MoviesPagedListModelDTO
import com.example.moviecatalog.data.data.remote.entities.kino_poisk.KPFilm
import com.example.moviecatalog.data.data.remote.entities.kino_poisk.MovieDirectorDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieService {
    @GET(MOVIE_PAGE)
    suspend fun getMoviePage(@Path("page") page: Int): MoviesPagedListModelDTO

    @GET(MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("id") id: String): MovieDetailsModelDTO

    @GET
    suspend fun getMovieByFilters(
        @Url url:String = GET_MOVIE_RATINGS,
        @Header("X-API-KEY") token: String = KP_TOKEN,
        @Query("keyword") keyword: String,
        @Query("yearFrom") year:Int,
    ):KPFilm

    @GET
    suspend fun getDirector(
        @Url url:String = GET_MOVIE_DIRECTOR,
        @Header("X-API-KEY") token: String = KP_TOKEN,
        @Query("name") name: String,
    ):MovieDirectorDTO
}