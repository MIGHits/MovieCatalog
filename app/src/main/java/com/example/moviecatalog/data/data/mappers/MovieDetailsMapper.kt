package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.MovieDetailsModelDTO
import com.example.moviecatalog.domain.entity.MovieDetailsModel

class MovieDetailsMapper(
    private val genreMapper: GenreMapper = GenreMapper(),
    private val reviewMapper: ReviewMapper = ReviewMapper()
) {
    fun map(movieDetails: MovieDetailsModelDTO): MovieDetailsModel {
        return MovieDetailsModel(
            id = movieDetails.id,
            name = movieDetails.name,
            poster = movieDetails.poster,
            year = movieDetails.year,
            country = movieDetails.country,
            genres = movieDetails.genres?.let { genreMapper.map(it) },
            reviews = movieDetails.reviews?.let { reviewMapper.mapFullReview(it) },
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