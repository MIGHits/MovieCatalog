package com.example.moviecatalog.data.data.remote.entities

import java.time.Year

data class MovieElementModelDTO(
    val id:String,
    val name:String?,
    val poster:String?,
    val year: Int,
    val country:String?,
    val genres:List<GenreModelDTO>?,
    val reviews:List<ReviewShortModelDTO>?

)
