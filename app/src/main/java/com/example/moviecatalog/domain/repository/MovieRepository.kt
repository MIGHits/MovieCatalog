package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.entity.MovieDetailsModel
import com.example.moviecatalog.domain.entity.MovieDirector
import com.example.moviecatalog.domain.entity.MovieServicesRating
import com.example.moviecatalog.domain.entity.MoviesPagedListModel

interface MovieRepository {
    suspend fun getMoviePage(page: Int): MoviesPagedListModel
    suspend fun getMovieDetails(id: String): MovieDetailsModel
    suspend fun getMovieRatings(name:String,year:Int):MovieServicesRating
    suspend fun getDirector(name:String):MovieDirector
}