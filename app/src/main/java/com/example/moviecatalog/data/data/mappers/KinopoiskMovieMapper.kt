package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.kino_poisk.KPFilm
import com.example.moviecatalog.domain.entity.MovieServicesRating

class KinopoiskMovieMapper {
    fun map(movie:KPFilm):MovieServicesRating{
        val movieRatings = movie.items?.first()
        return MovieServicesRating(
            movieRatings?.ratingKinopoisk,
            movieRatings?.ratingImdb
        )
    }
}