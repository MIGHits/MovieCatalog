package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.data.data.remote.entities.GenreModelDTO
import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.presentation.entity.GenreModelUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    fun reverseMap(genre: GenreModelUI): GenreModel {
        return GenreModel(
            id = genre.id,
            name = genre.name
        )
    }

    fun flowMap(flow: Flow<List<GenreModel>>): Flow<List<GenreModelUI>> {
        return flow.map { data -> data.map { GenreModelUI(name = it.name, id = it.id) } }
    }
}