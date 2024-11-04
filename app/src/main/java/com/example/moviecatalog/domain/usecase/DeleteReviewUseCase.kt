package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.repository.ReviewRepository

class DeleteReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(movieId: String, reviewId: String) {
        repository.deleteReview(movieId, reviewId)
    }
}