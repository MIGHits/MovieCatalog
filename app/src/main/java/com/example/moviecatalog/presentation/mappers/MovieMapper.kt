package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.domain.entity.MovieElementModel
import com.example.moviecatalog.presentation.entity.MovieElementModelUI

class MovieMapper {
    private fun map(movie: MovieElementModel,reviewMapper: ReviewMapper = ReviewMapper()): MovieElementModelUI {
        return MovieElementModelUI(
            name = movie.name,
            poster = movie.poster,
            country = movie.country,
            year = movie.year,
            genres = movie.genres,
            id = movie.id,
            reviews = reviewMapper.map(movie.reviews)
        )
    }

    fun map(movieList: List<MovieElementModel>): List<MovieElementModelUI> {
        return movieList.map { map(it) }
    }
}