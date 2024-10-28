package com.example.moviecatalog.presentation.view.adapter

import com.example.moviecatalog.domain.entity.GenreModel

data class MovieAdapterElement(
    val poster: String?,
    val id: String?,
    val movieTittle: String?,
    val genres: List<GenreModel>?
)
