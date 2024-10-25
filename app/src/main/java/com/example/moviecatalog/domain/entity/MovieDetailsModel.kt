package com.example.moviecatalog.domain.entity

import com.example.moviecatalog.data.data.remote.entities.GenreModelDTO
import com.example.moviecatalog.data.data.remote.entities.ReviewModelDTO

data class MovieDetailsModel(
    val id:String,
    val name:String?,
    val poster:String?,
    val year:Int,
    val country:String?,
    val genres:List<GenreModel>?,
    val reviews:List<ReviewModel>?,
    val time:Int,
    val tagline:String?,
    val description:String?,
    val director:String?,
    val budget:Int?,
    val fees:Int?,
    val ageLimit:Int
)
