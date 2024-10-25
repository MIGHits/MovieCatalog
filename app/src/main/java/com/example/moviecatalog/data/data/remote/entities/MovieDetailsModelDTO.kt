package com.example.moviecatalog.data.data.remote.entities

data class MovieDetailsModelDTO(
    val id:String,
    val name:String?,
    val poster:String?,
    val year:Int,
    val country:String?,
    val genres:List<GenreModelDTO>?,
    val reviews:List<ReviewModelDTO>?,
    val time:Int,
    val tagline:String?,
    val description:String?,
    val director:String?,
    val budget:Int?,
    val fees:Int?,
    val ageLimit:Int
    )
