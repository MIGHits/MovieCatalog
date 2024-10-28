package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.entity.MoviesPagedListModel

interface MovieRepository {
    suspend fun getMoviePage(page: Int): MoviesPagedListModel
    suspend fun getMovieDetails(id: String)
}