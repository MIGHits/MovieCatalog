package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.kino_poisk.MovieDirectorDTO
import com.example.moviecatalog.domain.entity.MovieDirector

class DirectorMapper {
    fun map(director: MovieDirectorDTO): MovieDirector {
        return if (director.items?.isNotEmpty() == true) {
            MovieDirector(director.items.first().posterUrl)
        } else {
            MovieDirector(null)
        }
    }
}