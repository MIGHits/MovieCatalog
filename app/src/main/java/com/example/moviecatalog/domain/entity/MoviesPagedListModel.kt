package com.example.moviecatalog.domain.entity


data class MoviesPagedListModel(
    val movies:List<MovieElementModel>?,
    val pageInfo: PageInfoModel
)
