package com.example.moviecatalog.data.data.remote.dataSource

import android.util.Log
import com.example.moviecatalog.data.data.remote.dataSource.RetrofitClientProvide.retrofitProvider
import com.example.moviecatalog.data.data.remote.entities.MovieDetailsModelDTO
import com.example.moviecatalog.data.data.remote.entities.MoviesPagedListModelDTO
import com.example.moviecatalog.data.data.remote.services.AuthService
import com.example.moviecatalog.data.data.remote.services.MovieService

class MovieServiceProvider():MovieService {
    private val movieServiceProvider = retrofitProvider.create(MovieService::class.java)
    override suspend fun getMoviePage(page: Int):MoviesPagedListModelDTO {
       return movieServiceProvider.getMoviePage(page)
    }

    override suspend fun getMovieDetails(id: String):MovieDetailsModelDTO {
       return movieServiceProvider.getMovieDetails(id)
    }

}