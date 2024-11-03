package com.example.moviecatalog.data.data.remote.entities.kino_poisk

data class MovieItemDTO(
    val countries: List<KPCountryDTO>,
    val genres: List<KPGenreDTO>,
    val imdbId: String?,
    val kinopoiskId: Int,
    val nameEn: String?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingImdb: Double?,
    val ratingKinopoisk: Double?,
    val type: String,
    val year: Int?
)