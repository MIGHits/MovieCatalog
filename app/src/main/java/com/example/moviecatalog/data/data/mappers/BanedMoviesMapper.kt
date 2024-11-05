package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.local.entities.BanedFilms
import com.example.moviecatalog.domain.entity.BanedFilm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BanedMoviesMapper {
    fun map(movie: BanedFilms): BanedFilm {
        return BanedFilm(
            id = movie.id,
            tittle = movie.tittle
        )
    }

    fun map(movieList: List<BanedFilms>): List<BanedFilm> {
        return movieList.map { map(it) }
    }
}