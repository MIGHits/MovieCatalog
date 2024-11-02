package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.data.data.remote.entities.GenreModelDTO
import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.presentation.entity.GenreModelUI

class GenreMapper {
    private fun map(genre: GenreModel): GenreModelUI {
        return GenreModelUI(
            id = genre.id,
            name = genre.name
        )
    }

    fun map(genreList: List<GenreModel>): List<GenreModelUI> {
        return genreList.map { map(it) }
    }
}