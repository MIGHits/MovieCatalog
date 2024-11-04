package com.example.moviecatalog.presentation.mappers

import com.example.moviecatalog.domain.entity.UserReview
import com.example.moviecatalog.presentation.entity.UserReviewUI

class UserReviewMapper {
    fun map(review: UserReviewUI): UserReview {
        return UserReview(
            reviewText = review.reviewText,
            rating = review.rating,
            isAnonymous = review.isAnonymous
        )
    }
}