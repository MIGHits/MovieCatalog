package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.domain.entity.MovieDetailsModel
import com.example.moviecatalog.presentation.entity.MovieDetailsModelUI

class MovieDetailsMapperUI(
    private val genreMapper: GenreMapper = GenreMapper(),
    private val reviewMapper: FullReviewMapper = FullReviewMapper()
) {
    fun map(movieDetails: MovieDetailsModel): MovieDetailsModelUI {
        return MovieDetailsModelUI(
            id = movieDetails.id,
            name = movieDetails.name,
            poster = movieDetails.poster,
            year = movieDetails.year,
            country = movieDetails.country,
            genres = movieDetails.genres?.let { genreMapper.map(it) },
            reviews = movieDetails.reviews?.let { reviewMapper.map(it) },
            time = movieDetails.time,
            tagline = movieDetails.tagline,
            description = movieDetails.description,
            director = movieDetails.director,
            budget = movieDetails.budget,
            fees = movieDetails.fees,
            ageLimit = movieDetails.ageLimit
        )
    }
}