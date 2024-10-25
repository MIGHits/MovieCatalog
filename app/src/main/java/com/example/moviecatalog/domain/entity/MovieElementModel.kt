package com.example.moviecatalog.domain.entity

import com.example.moviecatalog.data.data.remote.entities.GenreModelDTO

data class MovieElementModel(
    val id:String,
    val name:String?,
    val poster:String?,
    val year: Int,
    val country:String?,
    val genres:List<GenreModel>?
)
