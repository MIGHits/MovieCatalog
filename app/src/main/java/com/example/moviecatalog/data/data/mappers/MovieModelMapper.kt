package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.MovieElementModelDTO
import com.example.moviecatalog.domain.entity.MovieElementModel

class MovieModelMapper(private val genreMapper: GenreMapper){
    private fun map(movieDTO:MovieElementModelDTO):MovieElementModel{
        return MovieElementModel(
            id = movieDTO.id,
            name = movieDTO.name,
            poster = movieDTO.poster,
            year = movieDTO.year,
            country = movieDTO.country,
            genres = movieDTO.genres?.let { genreMapper.map(it) }
        )
    }
    fun map(movieListDTO:List<MovieElementModelDTO>):List<MovieElementModel>{
        return movieListDTO.map { map(it) }
    }
}