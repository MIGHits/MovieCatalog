package com.example.moviecatalog.data.data.remote.entities

data class MoviesPagedListModelDTO (
    val movies:List<MovieElementModelDTO>?,
    val pageInfo:PageInfoModelDTO
)