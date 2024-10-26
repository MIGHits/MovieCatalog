package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.GenreModelDTO
import com.example.moviecatalog.domain.entity.GenreModel

class GenreMapper {
    private fun map(genreDTO:GenreModelDTO):GenreModel{
        return GenreModel(
            id = genreDTO.id,
            name = genreDTO.name
        )
    }

    fun map(genreListDTO: List<GenreModelDTO>):List<GenreModel>{
        return genreListDTO.map { map(it) }
    }
}