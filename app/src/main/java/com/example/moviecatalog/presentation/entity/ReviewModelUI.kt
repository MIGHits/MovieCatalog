package com.example.moviecatalog.presentation.entity

data class ReviewModelUI (
    val id: String,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShortModelUI
)