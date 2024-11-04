package com.example.moviecatalog.data.data.remote.entities

data class UserReviewDTO(
    val reviewText:String,
    val rating: Int,
    val isAnonymous:Boolean
)
