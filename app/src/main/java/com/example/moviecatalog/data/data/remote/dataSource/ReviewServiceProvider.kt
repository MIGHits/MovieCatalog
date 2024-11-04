package com.example.moviecatalog.data.data.remote.dataSource

import com.example.moviecatalog.data.data.remote.dataSource.RetrofitClientProvide.retrofitProvider
import com.example.moviecatalog.data.data.remote.entities.UserReviewDTO
import com.example.moviecatalog.data.data.remote.services.ReviewService

class ReviewServiceProvider() : ReviewService {
    private val reviewServiceProvider = retrofitProvider.create(ReviewService::class.java)

    override suspend fun addReview(token: String, movieId: String, reviewBody: UserReviewDTO) {
        reviewServiceProvider.addReview(token, movieId, reviewBody)
    }

    override suspend fun updateReview(
        token: String,
        movieId: String,
        id: String,
        reviewBody: UserReviewDTO
    ) {
        reviewServiceProvider.updateReview(token, movieId, id, reviewBody)
    }

    override suspend fun deleteReview(token: String, movieId: String, id: String) {
        reviewServiceProvider.deleteReview(token, movieId, id)
    }

}