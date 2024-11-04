package com.example.moviecatalog.domain.usecase

import com.example.moviecatalog.domain.entity.UserReview
import com.example.moviecatalog.domain.repository.ReviewRepository

class EditReviewUseCase(private val repository: ReviewRepository){
    suspend operator fun invoke(movieId:String,reviewId:String,userReview: UserReview){
        repository.editReview(movieId,reviewId,userReview)
    }
}