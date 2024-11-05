package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.local.entities.FavoriteGenre
import com.example.moviecatalog.domain.entity.GenreModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DbGenresToDomain {

    fun map(genre: FavoriteGenre): GenreModel {
        return GenreModel(
            id = genre.id,
            name = genre.name
        )
    }

    fun map(flowList: Flow<List<FavoriteGenre>>): Flow<List<GenreModel>> {
        return flowList.map { data -> data.map { GenreModel(id = it.id, name = it.name) } }
    }
}