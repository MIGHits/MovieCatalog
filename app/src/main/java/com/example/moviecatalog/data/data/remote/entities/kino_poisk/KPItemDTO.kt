package com.example.moviecatalog.data.data.remote.entities.kino_poisk

data class KPItemDTO(
    val kinopoiskId: Int,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val sex: String,
    val webUrl: String
)