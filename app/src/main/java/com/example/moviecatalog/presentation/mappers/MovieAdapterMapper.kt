package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.R
import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.view.adapter.MovieAdapterElement

class MovieAdapterMapper {
    fun map(movie:MovieElementModelUI):MovieAdapterElement{
        val result:MovieAdapterElement = MovieAdapterElement(
            poster = movie.poster,
            id = movie.id,
            movieTittle = movie.name,
            genres = listOf(
                GenreModel("1",""),
                GenreModel("2",""),
                GenreModel("3",""))
        )
        for (i in 0..<(movie.genres?.size ?: 0)){
            if (i==3) break else {
                result.genres?.get(i)?.name = movie.genres?.get(i)?.name
                result.genres?.get(i)?.id = movie.genres?.get(i)?.id.toString()
            }
        }
        return  result
    }

    fun map(movieList:List<MovieElementModelUI>?):List<MovieAdapterElement>{
        return movieList?.map { map(it) } ?: emptyList()
    }
}