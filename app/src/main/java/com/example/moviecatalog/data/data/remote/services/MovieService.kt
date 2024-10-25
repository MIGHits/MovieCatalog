package com.example.moviecatalog.data.data.remote.services

import com.example.moviecatalog.data.data.remote.dataSource.URL.MOVIE_DETAILS
import com.example.moviecatalog.data.data.remote.dataSource.URL.MOVIE_PAGE
import com.example.moviecatalog.data.data.remote.entities.MovieDetailsModelDTO
import com.example.moviecatalog.data.data.remote.entities.MoviesPagedListModelDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET(MOVIE_PAGE)
    suspend fun getMoviePage(@Path("page") page:Int):MoviesPagedListModelDTO
    @GET(MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("id") id:String):MovieDetailsModelDTO
}