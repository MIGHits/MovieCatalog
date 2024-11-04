package com.example.moviecatalog.domain.entity

import androidx.resourceinspection.annotation.Attribute.IntMap

data class UserReview(
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)
