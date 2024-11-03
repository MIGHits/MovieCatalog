package com.example.moviecatalog.presentation.entity

import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.INITIAL_INT

data class MovieDetailsModelUI(
    val id: String = INITIAL_FIELD_STATE,
    val name: String? = null,
    val poster: String? = null,
    val year: Int = INITIAL_INT,
    val country: String? = null,
    val genres: List<GenreModelUI>? = null,
    val reviews: List<ReviewModelUI>? = null,
    val time: String = INITIAL_FIELD_STATE,
    val tagline: String? = null,
    val description: String? = null,
    val director: String? = null,
    val budget: Int? = null,
    val fees: Int? = null,
    val ageLimit: Int = INITIAL_INT,
    val kinopoiskRating:Double? = null,
    val imdbRating:Double? = null,
    val mcRating:Double? = null,
    val directorPoster:String? =null
)
