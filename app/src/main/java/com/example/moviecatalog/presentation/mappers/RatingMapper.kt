package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.domain.entity.MovieServicesRating
import com.example.moviecatalog.presentation.entity.MovieRating

class RatingMapper {
    fun map(ratings: MovieServicesRating): MovieRating {
        return MovieRating(ratings.ratingKinopoisk, ratings.ratingImdb)
    }
}