package com.example.moviecatalog.presentation.entity

import com.example.moviecatalog.common.Constants.MOVIE_COUNTRY
import com.example.moviecatalog.common.Constants.MOVIE_TITTLE
import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.domain.entity.ReviewModel
import com.example.moviecatalog.domain.entity.ReviewShortModel

data class MovieElementModelUI(
    val name: String? = MOVIE_TITTLE,
    val id: String,
    val poster: String? = null,
    val country: String? = MOVIE_COUNTRY,
    val year: Int? = null,
    val genres: List<GenreModel>? = null,
    val reviews: Double = 0.0
)
