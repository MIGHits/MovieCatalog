package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.kino_poisk.KPFilm
import com.example.moviecatalog.domain.entity.MovieServicesRating

class KinopoiskMovieMapper {
    fun map(movie: KPFilm): MovieServicesRating {
        return if (movie.items?.isNotEmpty() == true) {
            val movieRatings = movie.items.first()
            MovieServicesRating(
                movieRatings.ratingKinopoisk,
                movieRatings.ratingImdb
            )
        } else {
            MovieServicesRating(null, null)
        }
    }
}