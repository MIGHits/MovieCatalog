package com.example.moviecatalog.domain.entity

import com.example.moviecatalog.data.data.remote.entities.UserShortModelDTO

data class ReviewModel(
    val id: String,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShortModel
)
