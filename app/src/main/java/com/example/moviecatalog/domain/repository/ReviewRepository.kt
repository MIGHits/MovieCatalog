package com.example.moviecatalog.domain.repository

import com.example.moviecatalog.domain.entity.UserReview

interface ReviewRepository {
    suspend fun addReview(movieId: String, userReview: UserReview)
    suspend fun editReview(movieId: String, reviewId: String, userReview: UserReview)
    suspend fun deleteReview(movieId: String, reviewId: String)
}