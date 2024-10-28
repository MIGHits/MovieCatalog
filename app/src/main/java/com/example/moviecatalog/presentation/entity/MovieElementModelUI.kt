package com.example.moviecatalog.presentation.entity

import com.example.moviecatalog.common.Constants.MOVIE_COUNTRY
import com.example.moviecatalog.common.Constants.MOVIE_TITTLE
import com.example.moviecatalog.domain.entity.GenreModel

data class MovieElementModelUI(
    val name: String? = MOVIE_TITTLE,
    val id: String? = null,
    val poster: String? = null,
    val country: String? = MOVIE_COUNTRY,
    val year: Int? = null,
    val genres: List<GenreModel>? = null
)
