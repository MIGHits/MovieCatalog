package com.example.moviecatalog.data.data.remote.dataSource

import android.util.Log
import com.example.moviecatalog.data.data.remote.dataSource.RetrofitClientProvide.retrofitProvider
import com.example.moviecatalog.data.data.remote.entities.MovieDetailsModelDTO
import com.example.moviecatalog.data.data.remote.entities.MoviesPagedListModelDTO
import com.example.moviecatalog.data.data.remote.entities.kino_poisk.KPFilm
import com.example.moviecatalog.data.data.remote.entities.kino_poisk.MovieDirectorDTO
import com.example.moviecatalog.data.data.remote.services.MovieService

class MovieServiceProvider() : MovieService {
    private val movieServiceProvider = retrofitProvider.create(MovieService::class.java)
    override suspend fun getMoviePage(page: Int): MoviesPagedListModelDTO {
        return movieServiceProvider.getMoviePage(page)
    }

    override suspend fun getMovieDetails(id: String): MovieDetailsModelDTO {
        return movieServiceProvider.getMovieDetails(id)
    }

    override suspend fun getMovieByFilters(
        url: String,
        token: String,
        keyword: String,
        year: Int
    ): KPFilm {
        val result = movieServiceProvider.getMovieByFilters(keyword = keyword, year = year)
        Log.d("Api",result.items?.first()?.ratingKinopoisk.toString())
        return result
    }

    override suspend fun getDirector(url: String, token: String, name: String): MovieDirectorDTO {
        val result = movieServiceProvider.getDirector(name = name)
        return result
    }


}