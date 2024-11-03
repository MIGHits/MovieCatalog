package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.domain.entity.MovieDetailsModel
import com.example.moviecatalog.domain.entity.ReviewModel
import com.example.moviecatalog.presentation.entity.MovieDetailsModelUI
import java.text.DecimalFormatSymbols
import java.util.Locale

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
            time = formatTime(movieDetails.time),
            tagline = movieDetails.tagline,
            description = movieDetails.description,
            director = movieDetails.director,
            budget = movieDetails.budget,
            fees = movieDetails.fees,
            ageLimit = movieDetails.ageLimit,
            mcRating = countRating(movieDetails.reviews)
        )
    }

    private fun countRating(reviewList: List<ReviewModel>?): Double {
        var result = 0.0
        reviewList?.forEach { review -> result += review.rating }
        return (result / (reviewList?.size ?: 1))
    }

    private fun formatTime(time: Int): String {
        val hours = time / 60
        val minutes = time % 60

        return if (minutes != 0) {
            "$hours ч $minutes мин"
        } else {
            "$hours ч"
        }
    }
}