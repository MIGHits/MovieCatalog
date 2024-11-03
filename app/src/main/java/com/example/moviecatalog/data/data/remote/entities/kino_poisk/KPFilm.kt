package com.example.moviecatalog.data.data.remote.entities.kino_poisk

data class KPFilm(
    val items: List<MovieItemDTO>?,
    val total: Int,
    val totalPages: Int
)