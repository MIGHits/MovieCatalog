package com.example.moviecatalog.data.data.repository

import com.example.moviecatalog.data.data.mappers.UserReviewMapper
import com.example.moviecatalog.data.data.remote.dataSource.ReviewServiceProvider
import com.example.moviecatalog.data.data.remote.services.ReviewService
import com.example.moviecatalog.data.data.storage.TokenStorage
import com.example.moviecatalog.domain.entity.UserReview
import com.example.moviecatalog.domain.repository.ReviewRepository

class ReviewRepositoryImpl(
    private val tokenStorage: TokenStorage,
    private val reviewApi: ReviewService,
    private val reviewMapper: UserReviewMapper
) : ReviewRepository {
    val token = tokenStorage.getToken().token

    override suspend fun addReview(movieId: String, userReview: UserReview) {
        reviewApi.addReview(
            token,
            movieId,
            reviewMapper.map(userReview)
        )
    }

    override suspend fun editReview(movieId: String, reviewId: String, userReview: UserReview) {
        reviewApi.updateReview(
            token,
            movieId,
            reviewId,
            reviewMapper.map(userReview)
        )
    }

    override suspend fun deleteReview(movieId: String, reviewId: String) {
        reviewApi.deleteReview(token, movieId, reviewId)
    }
}