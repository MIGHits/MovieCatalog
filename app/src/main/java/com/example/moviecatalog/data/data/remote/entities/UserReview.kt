package com.example.moviecatalog.data.data.remote.entities

import com.example.moviecatalog.domain.entity.MovieServicesRating

data class UserReview(
    val reviewText:String,
    val rating: Int,
    val isAnonymous:Boolean
)
