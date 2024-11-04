package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.UserReview
import com.example.moviecatalog.domain.repository.ReviewRepository

class AddReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(movieId: String, userReview: UserReview) {
        repository.addReview(movieId,userReview)
    }
}